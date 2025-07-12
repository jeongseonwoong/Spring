package RabbitMQ.RabbitMQ.step8;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendShipping(String message){
        rabbitTemplate.convertAndSend(RabbitMQConfig8.ORDER_TOPIC_EXCHANGE,"order.completed",message);
        System.out.println("[주문 완료, 배송 지시 메시지 생성: " + message + "]");
    }
}
