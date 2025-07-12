package RabbitMQ.RabbitMQ.step7;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OrderConsumer {
    private static final int MAX_RETRIES = 3;
    private int retryCount = 0;

    /**
     * containerFactory는 RabbitMQManualConfig7에서 설정한 rabbitListenerContainerFactory()
     * @param message 메시지
     * @param channel Ack,Nack 처리할 때 필요함.
     * @param tag 메시지가 가지고 있는 고유번호
     */
    @RabbitListener(queues = RabbitMQConfig7.ORDER_COMPLETED_QUEUE, containerFactory = "rabbitListenerContainerFactory")
    public void processOrder(String message, Channel channel, @Header("amqp_deliveryTag") long tag){
        try {
            //RabbitMQ의 메시지 소비는 **"메시지 1건 단위로 처리 후 실패 시 재전송"**되는 구조
            //예외 발생 시 메시지를 다시 큐에 requeue 후 재전송 시도.
            if("fail".equalsIgnoreCase(message)){
                if(retryCount < MAX_RETRIES){
                    System.out.println("Fail & Retry: " + retryCount);
                    retryCount++;
                    throw new RuntimeException(message);
                }else{
                    System.out.println("최대 횟수 초과, DLQ 이동시킴");
                    retryCount = 0;
                    channel.basicNack(tag,false,false);
                    return;
                }
            }
            //성공 처리
            System.out.println("성공: " + message);
            channel.basicAck(tag,false);
            retryCount=0;
        }catch (Exception e){
            System.out.println("error 발생: "+ e.getMessage());
            try{
                //실패 시 basicReject 재처리 정송
                channel.basicReject(tag,true);
            }catch (IOException e1){
                System.out.println("fail & reject message : " + e1.getMessage());
            }
        }
    }

}
