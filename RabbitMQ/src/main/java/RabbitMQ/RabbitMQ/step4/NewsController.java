package RabbitMQ.RabbitMQ.step4;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class NewsController {

    private final NewsPublisher newsPublisher;

    //웹 소켓 기반으로 요청이 왔을 때 MessageMapping 사용
    @MessageMapping("/subscribe")
    public void handleSubscribe(@Header("newsType")String newsType){
        System.out.println("newsType: " + newsType);
        String newsMessage = newsPublisher.publish(newsType);
        System.out.println("newsMessage: " + newsMessage);
    }
}
