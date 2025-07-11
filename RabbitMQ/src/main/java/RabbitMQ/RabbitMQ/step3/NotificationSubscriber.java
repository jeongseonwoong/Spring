package RabbitMQ.RabbitMQ.step3;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Component
@RequiredArgsConstructor
public class NotificationSubscriber {

    public static final String CLIENT_URL = "/topic/notifications";

    //웹소켓으로 메시지를 전달하기 위한 Spring의 템플릿 클래스.
    private final SimpMessagingTemplate simpMessagingTemplate;

    /**
     * @RabbitListener(queues = RabbitMQConfig3.QUEUE_NAME)의 기능
     * String message = (String) rabbitTemplate.receiveAndConvert(RabbitMQConfig3.QUEUE_NAME,"",message);
     * if(!message.isNull())
     *  simpleMessagingTemplate.convertAndSend(CLIENT_URL,message);
     *  을 간단하게 아래처럼 수행 가능.
     */
    @RabbitListener(queues = RabbitMQConfig3.QUEUE_NAME)
    public void subscriber(String message){
        //Exchange로부터 Queue에서 메시지 수신
        System.out.println("Received Notification: "+ message);

        //convertAndSend를 통해 특정 경로로 메시지를 전달함.
        simpMessagingTemplate.convertAndSend(CLIENT_URL,message);
    }
}
