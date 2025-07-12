package RabbitMQ.RabbitMQ.step8;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig8 {

    public static final String ORDER_COMPLETED_QUEUE = "orderCompletedQueue";
    public static final String ORDER_TOPIC_EXCHANGE = "orderCompletedExchange";

    public static final String DLQ = "deadLetterQueue";
    public static final String ORDER_TOPIC_DLX = "deadLetterExchange";

    public static final String DEAD_LETTER_ROUTING_KEY = "dead.letter";

    @Bean
    public TopicExchange orderExchange(){
        return new TopicExchange(ORDER_TOPIC_EXCHANGE);
    }

    // 실패 건들을 처리하는 exchange
    @Bean
    public TopicExchange deadLetterExchange(){
        return new TopicExchange(ORDER_TOPIC_DLX);
    }

    // 메시지가 처리되지 못했을 경우 자동으로 DeadLetterQueue로 이동시킴
    @Bean
    public Queue orderQueue(){
        return QueueBuilder.durable(ORDER_COMPLETED_QUEUE) //지속성 Order Completed Queue 만들고
                .withArgument("x-dead-letter-exchange",ORDER_TOPIC_DLX) // 실패 시 Dead Letter Exchange 설정
                .withArgument("x-dead-letter-routing-key",DLQ) // 실패 시 사용될 Dead Letter Queue 설정
                .ttl(5000)
                .build();
    }

    @Bean
    public Queue deadLetterQueue(){
        return QueueBuilder.durable(DLQ).build();
    }

    @Bean
    public Binding orderCompletedBinding(){
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with("order.completed");
    }

    @Bean
    public Binding deadLetterBinding(){
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DEAD_LETTER_ROUTING_KEY);
    }
}
