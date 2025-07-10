package RabbitMQ.RabbitMQ.step2;

import RabbitMQ.RabbitMQ.step1.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController2 {

    private final WorkQueueProducer workQueueProducer;

    @PostMapping("/workQueue")
    public String sendMessage(@RequestBody String message, @RequestParam int duration) {
        workQueueProducer.send(message,duration);
        return "Work queue sent = " + message + ", (" + duration + ")";
    }

}
