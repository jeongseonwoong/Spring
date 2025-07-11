package RabbitMQ.RabbitMQ.step5;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LogConsumer {

    @RabbitListener(queues = RabbitMQConfig5.ERROR_QUEUE)
    public void consumeError(String message){
        System.out.println("Error를 받음: " + message );
    }

    @RabbitListener(queues = RabbitMQConfig5.WARN_QUEUE)
    public void consumeWarn(String message){
        System.out.println("Warn를 받음: " + message );
    }

    @RabbitListener(queues = RabbitMQConfig5.INFO_QUEUE)
    public void consumeInfo(String message){
        System.out.println("Info를 받음: " + message );
    }

    @RabbitListener(queues = RabbitMQConfig5.ALL_LOG_QUEUE)
    public void consumeAllLogs(String message){
        System.out.println("로그를 받음:" + message);
    }
}
