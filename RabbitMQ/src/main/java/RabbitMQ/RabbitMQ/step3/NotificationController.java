package RabbitMQ.RabbitMQ.step3;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationPublisher publisher;

    @PostMapping
    public String sendNotification(@RequestBody String message){
        publisher.publish(message);
        return "Notification Send: " + message + "\n";
    }

}
