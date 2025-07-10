package RabbitMQ.RabbitMQ.step2;

import RabbitMQ.RabbitMQ.step1.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkQueueProducer {

    private final RabbitTemplate rabbitTemplate;

    public void send(String workQueueMessage, int duration){
        String message = workQueueMessage + "| " + duration;
        rabbitTemplate.convertAndSend(RabbitMQConfig2.QUEUE_NAME,message);
        System.out.println("Sent work queue: " + message);
    }


}
