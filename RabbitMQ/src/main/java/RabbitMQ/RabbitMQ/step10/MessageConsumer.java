package RabbitMQ.RabbitMQ.step10;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OptionalDataException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class MessageConsumer {
    private final StockRepository stockRepository;

    public MessageConsumer(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @RabbitListener(queues = RabbitMQConfig10.QUEUE_NAME, containerFactory = "rabbitListenerContainerFactory")
    public void receiveMessage(StockDTO stock,
                               @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                               Channel channel) {
        try {
            System.out.println("[Consumer] " + stock);
            Thread.sleep(200);
            Optional<StockEntity> optionalStock = stockRepository.findById(Long.valueOf(stock.getUserId()));
            if (optionalStock.isPresent()) {
                StockEntity stockEntity = optionalStock.get();
                stockRepository.save(stockEntity); // 메시지를 MQ에서 받아 처리한 다음, 해당 엔티티의 상태를 DB에 반영하려고 저장하는 것 예를 들어 processed = true, 처리시간, 소비자 로그, 결과값 등 변경 목적
                System.out.println("[Save Entity Consumer] " + stockEntity);
            } else {
                throw new RuntimeException("Stock not found");
            }

            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            System.out.println("[Consumer Error] " + e.getMessage());
            try {
                channel.basicNack(deliveryTag, false, false);

            } catch (IOException ex) {
                System.out.println("[Consumer send nack] " + ex.getMessage());
                //throw new RuntimeException(ex);
            }
        }
    }
}