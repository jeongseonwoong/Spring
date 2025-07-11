package RabbitMQ.RabbitMQ.step4;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsSubscriber {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @RabbitListener(queues = RabbitMQConfig4.JAVA_QUEUE)
    public void javaNews(String message){
        simpMessagingTemplate.convertAndSend("/topic/java",message);
    }

    @RabbitListener(queues = RabbitMQConfig4.SPRING_QUEUE)
    public void springNews(String message){
        simpMessagingTemplate.convertAndSend("/topic/spring", message);
    }

    @RabbitListener(queues = RabbitMQConfig4.VUE_QUEUE)
    public void vueNews(String message){
        simpMessagingTemplate.convertAndSend("/topic/vue",message);
    }
}
