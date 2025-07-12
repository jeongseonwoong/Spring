package RabbitMQ.RabbitMQ.step9;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageConsumer {
    private final StockRepository stockRepository;

    @RabbitListener(queues = "transactionQueue")
    public void receiveTransaction(StockDTO stockDTO){
        System.out.println("received message = " + stockDTO);
        try{
            stockDTO.setProcessed(true);
            stockRepository.save(StockEntity.from(stockDTO));
            System.out.println("StockEntity 저장 완료");
        }catch (Exception e){
            System.out.println("Entity 수정 에러" + e.getMessage());
            throw e; // todo 메시지를 데드레터 큐에 집어 넣는다.
        }
    }
}
