package RabbitMQ.RabbitMQ.step8;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    private final RabbitTemplate rabbitTemplate;
    private final RetryTemplate retryTemplate;

    public OrderConsumer(RabbitTemplate rabbitTemplate, RetryTemplate retryTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.retryTemplate = retryTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig8.ORDER_COMPLETED_QUEUE)
    public void consume(String message) {
        System.out.println("message Received: " + message);
        if("fail".equalsIgnoreCase(message)){
            throw new RuntimeException("Processing fail Retry");
        }
        System.out.println("Message processed successfully:" + message);
    }
}