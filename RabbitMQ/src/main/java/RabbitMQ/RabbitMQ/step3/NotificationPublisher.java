package RabbitMQ.RabbitMQ.step3;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(String message){
        //FanoutExchange는 모든 연결된 키에 메시지를 넘겨주므로 라우팅 키가 따로 필요하지 않으므로 라우팅 키는 공백
        rabbitTemplate.convertAndSend(RabbitMQConfig3.FANOUT_EXCHANGE,"",message);
        System.out.println("Published Notification: " + message);
    }

}
