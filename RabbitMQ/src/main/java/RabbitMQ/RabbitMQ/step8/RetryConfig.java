package RabbitMQ.RabbitMQ.step7;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RetryConfig {
    
    @Bean
    public RetryTemplate retryTemplate()
}
