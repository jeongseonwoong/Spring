package RabbitMQ.RabbitMQ.step10;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig10 {
    public static final String QUEUE_NAME = "transactionQueue";
    public static final String EXCHANGE_NAME = "transactionExchange";
    public static final String ROUTING_KEY = "transactionRoutingKey";

    // Queue 설정
    @Bean
    public Queue transactionQueue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", "") // Dead Letter Exchange
                .withArgument("x-dead-letter-routing-key", "deadLetterQueue") // Dead Letter Routing Key
                .build();
    }

    // Dead Letter Queue 설정
    @Bean
    public Queue deadLetterQueue() {
        return new Queue("deadLetterQueue");
    }

    // Exchange 설정
    @Bean
    public DirectExchange transactionExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // Binding 설정
    @Bean
    public Binding transactionBinding(Queue transactionQueue, DirectExchange transactionExchange) {
        return BindingBuilder.bind(transactionQueue).to(transactionExchange).with(ROUTING_KEY);
    }

    // 메시지 변환기 설정
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // TODO RabbitTemplate 설정, ReturnsCallback 활성화 등록, ConfirmCallback 설정
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter()); // JSON 변환기
        rabbitTemplate.setMandatory(true);  // ReturnCallback 활성화

        // confirmCallBack 설정 이건 비동기 confirm 결과를 로그로 찍는 용도
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("#### [Message confirmed]: " +
                        (correlationData != null ? correlationData.getId() : "null"));
            } else {
                System.out.println("#### [Message not confirmed]: " +
                        (correlationData != null ? correlationData.getId() : "null") + ", Reason: " + cause);

                // 실패 메시지에 대한 추가 처리 로직 (예: 로그 기록, DB 적재, 관리자 알림 등)
            }
        });

        // ReturnCallback 설정 ReturnCallback은 메시지가 브로커까지는 도달했지만 → 해당 큐로 라우팅되지 못했을 때 호출되는 콜백이야.
        rabbitTemplate.setReturnsCallback(returned -> {
            System.out.println("Return Message: " + returned.getMessage().getBody());
            System.out.println("Exchange : " + returned.getExchange());
            System.out.println("RoutingKey : " + returned.getRoutingKey());

            // 데드레터 설정 추가
        });
        return rabbitTemplate;
    }


    // TODO RabbitListener 설정, 수동 Ack 모드 설정
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 수동 Ack 모드
        return factory;
    }

}