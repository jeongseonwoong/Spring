package RabbitMQ.RabbitMQ.step8;

import RabbitMQ.RabbitMQ.step7.RabbitMQConfig7;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDLQConsumer {
    //DLQ를 처리하는 로직
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig7.DLQ)
    public void process(String message){
        System.out.println("DLQ Message Received: " + message);
        try {
            String fixMessage = "success";
            rabbitTemplate.convertAndSend(RabbitMQConfig7.ORDER_EXCHANGE,"order.completed.shipping",fixMessage);
            System.out.println("DLQ Message Sent: " + fixMessage);
        }catch (Exception e){
            System.out.println("DLQ Consumer Error" + e.getMessage());
        }
    }
}
