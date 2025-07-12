package RabbitMQ.RabbitMQ.step10;


import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;
    private final StockRepository stockRepository;

    public MessageProducer(RabbitTemplate rabbitTemplate, StockRepository stockRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.stockRepository = stockRepository;
    }


    @Transactional
    public void sendMessage(StockDTO stockDTO, boolean testCase) {
        stockDTO.setProcessed(false);
        //메시지를 전송하기 전에, DB에 엔티티를 일단 기록해야 MQ 메시지에도 ID 같은 정보가 들어갈 수 있음 (PK 필요)
        StockEntity entity = stockRepository.save(StockEntity.from(stockDTO));

        System.out.println("[producer entity] : " + entity);

        if (stockDTO.getUserId() == null || stockDTO.getUserId().isEmpty()) {
            throw new RuntimeException("User id is required");
        }

        try {
            // 메시지를 rabbitmq 에 전송
            CorrelationData correlationData = new CorrelationData(entity.getId().toString());
            rabbitTemplate.convertAndSend(
                    testCase ? "nonExistentExchange" : RabbitMQConfig10.EXCHANGE_NAME,
                    testCase ? "invalidRoutingKey" : RabbitMQConfig10.ROUTING_KEY,
                    entity,
                    correlationData
            );

            /**
             * CorrelationData는 메시지를 구분할 수 있는 고유 식별자
             * convertAndSend(...)는 메시지를 보냄 + correlationData를 등록함
             * getFuture().get().isAck()를 통해 confirm 결과를 5초까지 기다림
             * 성공 (ack)이면 DB 저장
             * 실패 (nack)면 예외 발생 → @Transactional에 의해 전체 롤백
             */
            if (correlationData.getFuture().get(5, TimeUnit.SECONDS).isAck()) {
                System.out.println("[producer correlationData] 성공" + entity);
                // 메시지 전송 후 **Publisher Confirms(ACK)**가 성공한 경우, 그에 따라 DB 상태를 추가 업데이트하려는 목적
                stockRepository.save(entity);
            } else {
                throw new RuntimeException("# confirm 실패 - 롤백");
            }

        } catch (Exception e) {
            System.out.println("[producer exception fail] : " + e);
            throw new RuntimeException(e);
        }
    }
}