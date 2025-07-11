package RabbitMQ.RabbitMQ.step3;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/send")
    public void sendMessage(NotificationMessage notificationMessage){
        // 수신된 메시지를 브로드캐스팅
        String message = notificationMessage.getMessage();
        System.out.println("message = "+message);
        //클라이언트에 메시지 바로 브로드캐스팅
        simpMessagingTemplate.convertAndSend("/topic/notifications",message);
    }
}
