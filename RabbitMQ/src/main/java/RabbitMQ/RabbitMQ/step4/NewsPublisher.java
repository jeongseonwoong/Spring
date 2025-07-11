package RabbitMQ.RabbitMQ.step4;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsPublisher {

    private final RabbitTemplate rabbitTemplate;

    private String publishMessage(String newsType, String messageSuffix){
        String message = newsType + messageSuffix;
        // 각 뉴스 별로 라우팅 키를 걸음 FANOUT 인데 왜 라우팅 키를? 하나의 익스체인지에 여러 큐가 연결되어 있으니까? 라우팅 키(큐이름)으로 전달하도록 해줌
        rabbitTemplate.convertAndSend(RabbitMQConfig4.FANOUT_EXCHANGE_FOR_NEWS,newsType,message);
        System.out.println("News Published: " + message);
        return message;
    }

    public String publish(String newsType) {
        return publishMessage(newsType," 관련 새 소식이 있어요!");
    }

    public String publishAPI(String newsType) {
        return publishMessage(newsType," - 관련 새 소식이 나왔습니다. (API)");
    }

}
