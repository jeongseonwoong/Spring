package RabbitMQ.RabbitMQ.step5;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig5 {

    //큐 이름을 정의
    public static final String ERROR_QUEUE = "error_queue";
    public static final String WARN_QUEUE = "warn_queue";
    public static final String INFO_QUEUE = "info_queue";
    public static final String ALL_LOG_QUEUE = "all_log_queue";


    public static final String TOPIC_EXCHANGE = "topic_exchange";

    @Bean
    public Queue errorQueue(){
        return new Queue(ERROR_QUEUE,false);
    }

    @Bean
    public Queue warnQueue(){
        return new Queue(WARN_QUEUE,false);
    }

    @Bean
    public Queue infoQueue(){
        return new Queue(INFO_QUEUE,false);
    }

    @Bean
    public Queue allLogQueue(){
        return new Queue(ALL_LOG_QUEUE,false);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding errorBinding(){
        return BindingBuilder.bind(errorQueue()).to(topicExchange()).with("log.error");
    }

    @Bean
    public Binding warnBinding(){
        return BindingBuilder.bind(warnQueue()).to(topicExchange()).with("log.warn");
    }

    @Bean
    public Binding infoBinding(){
        return BindingBuilder.bind(infoQueue()).to(topicExchange()).with("log.info");
    }

    @Bean
    public Binding allLogBinding(){
        return BindingBuilder.bind(allLogQueue()).to(topicExchange()).with("log.*");
    }
}
