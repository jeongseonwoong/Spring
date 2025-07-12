package RabbitMQ.RabbitMQ.step8;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDeadLetterRetry {
    //DLQ를 처리하는 로직
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig8.DLQ)
    public void process(String message){
        System.out.println("DLQ Message Received: " + message);
        try {
            if("fail".equalsIgnoreCase(message)){
                message = "success";
                System.out.println("Message fixed:" + message);
            }else{
                System.out.println("Message already fixed: " + message);
                return;
            }
            rabbitTemplate.convertAndSend(RabbitMQConfig8.ORDER_TOPIC_EXCHANGE,"order.completed",message);
            System.out.println("DLQ Message Sent: " + message);
        }catch (Exception e){
            System.out.println("DLQ Consumer Error" + e.getMessage());
        }
    }
}
