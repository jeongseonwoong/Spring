package RabbitMQ.RabbitMQ.step0;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    /**
     * Queue queue()
     * 역할: 이 Bean은 Queue 인스턴스를 생성하고, 애플리케이션이 사용할 RabbitMQ 큐를 정의합니다.
     * •	QUEUE_NAME은 메시지가 쌓이고 처리될 큐의 이름을 정의합니다. 이 예제에서는 helloQueue로 설정되어 있습니다.
     * •	false 파라미터는 큐가 휘발성(volatile)인지 영속성(persistent)인지 여부를 지정하는 옵션입니다. false로 설정하면 서버가 종료되거나 재시작될 때 큐의 메시지가 사라집니다.
     * •	사용 용도: 메시지를 전달하고 처리하는 기본 큐를 설정하는 데 사용됩니다.
     * <p>
     * RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory)
     * 역할: RabbitMQ와의 통신을 위한 템플릿 클래스 RabbitTemplate의 인스턴스를 생성합니다. 이 템플릿은 메시지를 보내고 받을 때 주로 사용됩니다.
     * •	RabbitTemplate은 Spring의 JdbcTemplate과 비슷하게, RabbitMQ와 상호작용하기 위한 간단한 API를 제공합니다. 주로 메시지 전송을 담당합니다.
     * •	ConnectionFactory는 RabbitMQ와의 연결을 관리하는 객체로, rabbitTemplate에 주입하여 메시지를 전송할 때 사용할 연결을 제공합니다.
     * •	사용 용도: 메시지를 전송하는 Sender가 rabbitTemplate.convertAndSend() 메서드를 사용해 큐에 메시지를 넣는 데 사용합니다.
     * <p>
     * SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter)
     * 역할: 이 Bean은 RabbitMQ 메시지를 비동기적으로 수신하기 위해 SimpleMessageListenerContainer를 생성합니다. 이 컨테이너는 특정 큐를 지속적으로 모니터링하고,
     * 메시지를 수신하면 지정된 리스너(MessageListenerAdapter)를 통해 처리합니다.
     * •	ConnectionFactory는 RabbitMQ와 연결을 유지하며, 수신하는 메시지를 이 연결을 통해 가져옵니다.
     * •	setQueueNames(QUEUE_NAME) 메서드는 특정 큐 이름을 설정합니다. 이 컨테이너는 helloQueue에서 수신되는 메시지를 모니터링합니다.
     * •	setMessageListener(listenerAdapter)는 listenerAdapter를 설정하여, 메시지가 수신될 때 호출할 리스너를 지정합니다.
     * •   Spring AMQP에서 메시지를 자동으로 수신하려면 SimpleMessageListenerContainer가 필요합니다.
     * •	메시지 수신과 관련된 스레드 관리, 연결 유지, 큐 모니터링 작업을 자동으로 처리합니다.
     * •	없이 구현하면?:
     * •	RabbitTemplate의 receiveAndConvert() 메서드를 수동으로 호출하여 메시지를 가져오는 동기적 방식으로 구현해야 합니다.
     * •   이는 비효율적이며, 큐의 상태를 주기적으로 확인(polling)해야 하기 때문에 자원을 낭비할 수 있습니다.
     * <p>
     * MessageListenerAdapter listenerAdapter(Receiver receiver)
     * 역할: MessageListenerAdapter는 수신한 메시지를 특정 클래스의 특정 메서드로 전달하는 어댑터 역할을 합니다.
     * •	receiver 객체는 메시지를 처리하는 역할을 하는 빈이며, receiveMessage 메서드를 호출합니다.
     * •	MessageListenerAdapter는 RabbitMQ에서 수신된 메시지를 특정 메서드에 전달할 수 있도록 해 줍니다.
     * 이 경우, receiveMessage 메서드가 자동으로 호출되며, 메시지 내용을 인자로 받습니다.
     * •	Receiver 클래스의 receiveMessage 메서드가 메시지를 수신하여 처리할 수 있도록 설정합니다.
     * RabbitMQ에서 수신된 메시지가 receiver.receiveMessage(String message) 메서드로 전달됩니다.
     */

    public static final String QUEUE_NAME = "helloqueue";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(Receiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage");
    }
}
