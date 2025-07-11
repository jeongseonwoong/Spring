package RabbitMQ.RabbitMQ.step7;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig7 {

    public static final String ORDER_COMPLETED_QUEUE = "order_completed_queue";
    public static final String ORDER_EXCHANGE = "order_completed_exchange";
    public static final String DLQ = "deadLetterQueue";
    public static final String DLX = "deadLetterExhange";

    @Bean
    public TopicExchange orderExchange(){
        return new TopicExchange(ORDER_EXCHANGE);
    }

    // 실패 건들을 처리하는 exchange
    @Bean
    public TopicExchange deadLetterExchange(){
        return new TopicExchange(DLX);
    }

    // 메시지가 처리되지 못했을 경우 자동으로 DeadLetterQueue로 이동시킴
    @Bean
    public Queue orderQueue(){
        return QueueBuilder.durable(ORDER_COMPLETED_QUEUE) //지속성 Order Completed Queue 만들고
                .withArgument("x-dead-letter-exchange",DLX) // 실패 시 Dead Letter Exchange 설정
                .withArgument("x-dead-letter-routing-key",DLQ) // 실패 시 사용될 Dead Letter Queue 설정
                .ttl(5000)
                .build();
    }

    @Bean
    public Queue deadLetterQueue(){
        return new Queue(DLQ);
    }

    @Bean
    public Binding orderCompletedBinding(){
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with("order.completed.#");
    }

    @Bean
    public Binding deadLetterBinding(){
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DLQ);
    }
}
