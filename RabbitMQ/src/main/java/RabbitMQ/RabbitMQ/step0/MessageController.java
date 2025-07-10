package RabbitMQ.RabbitMQ.step0;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final Sender sender;

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) {
        sender.send(message);
        return "Message sent successfully";
    }

}
