package RabbitMQ.RabbitMQ.step7;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQManualConfig7 {


    /**
     *     @Bean
     *     public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){
     *         SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
     *         container.setConnectionFactory(connectionFactory);
     *         container.setQueueNames(QUEUE_NAME);
     *         container.setMessageListener(listenerAdapter);
     *         container.setAcknowledgeMode(AcknowledgeMode.AUTO);
     *         return container;
     *     }
     *     아래는 이거의 최신 버전. 위와같은 방식으로 acknowledgeMode 등을 선턱할 수 있지만
     *
     *     @Bean
     *     public MessageListenerAdapter listenerAdapter(WorkQueueConsumer workQueueConsumer){
     *         return new MessageListenerAdapter(workQueueConsumer,"workQueueTask");
     *     }
     *     이렇게 Subscribe하는 클래스와 메서드를 명시적으로 적어줘야해서 @RAbbitListener를  사용하기 위해선 아래와 같은 최신 방법을 추천한다.
     *
     */

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //수동모드 설정이 들어가야 한다.
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
}
