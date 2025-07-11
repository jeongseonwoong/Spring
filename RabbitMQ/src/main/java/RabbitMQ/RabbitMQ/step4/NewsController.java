package RabbitMQ.RabbitMQ.step4;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class NewsController {

    private final NewsPublisher newsPublisher;

    @MessageMapping("/subscribe")
    public void handleSubscribe(@Header("newsType")String newsType){
        System.out.println("newsType: " + newsType);
        String newsMessage = newsPublisher.publish(newsType);
        System.out.println("newsMessage: " + newsMessage);
    }
}
