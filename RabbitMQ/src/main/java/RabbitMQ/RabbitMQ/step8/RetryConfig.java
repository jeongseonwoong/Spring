package RabbitMQ.RabbitMQ.step8;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RetryConfig {
    
    @Bean
    public RetryTemplate retryTemplate(){
        RetryTemplate retryTemplate = new RetryTemplate();
        //재시도 정책 설정: 최대 3번 시도
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        // 백오프 정책 설정: 재시도 간격 1초
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(1000L);
        //재시도 정책과 백오프 정책 재시도 템플릿에 넣고 리턴
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }
}
