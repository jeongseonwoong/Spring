package RabbitMQ.RabbitMQ.step9;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MessageProducer {
    private final StockRepository stockRepository;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public void sendMessage(StockDTO stockDTO, String testCase){
        rabbitTemplate.execute(channel -> {
            try{
                channel.txSelect(); //트랜젝션 시작
                stockDTO.setProcessed(false); // 컨슈머에서 제대로 받았을 경우 true 값으로 update;
                StockEntity stock = stockRepository.save(StockEntity.from(stockDTO));
                System.out.println("Stock saved: " + stock);

                //메시지 발행
                rabbitTemplate.convertAndSend("transactionalQueue",new StockDTO(stock));

                if("fail".equalsIgnoreCase(testCase)){
                    throw new RuntimeException("트렌젝션 작업 중에 에러 발생");
                }

                channel.txCommit();
                System.out.println("트랜젝션이 정상적으로 처리 되었음");
            }catch (Exception e){
                System.out.println("트랜젝션 실패: " + e.getMessage());
                channel.txRollback();
                throw new RuntimeException("트랜젝션 롤백 완료", e);
            }finally {
                if(channel != null){
                    try{
                        channel.close();
                    }catch (Exception e){
                        e.printStackTrace();;
                    }
                }
            }
            return null;
        });
    }
}
