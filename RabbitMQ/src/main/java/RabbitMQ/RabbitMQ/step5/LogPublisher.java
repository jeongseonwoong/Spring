package RabbitMQ.RabbitMQ.step5;

import RabbitMQ.RabbitMQ.step4.RabbitMQConfig4;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(String routingKey,String message){
        rabbitTemplate.convertAndSend(RabbitMQConfig5.DIRECT_EXCHANGE,routingKey,message);
        System.out.println("message published: "+ routingKey + " " + message);
    }
}
