package Spring.Basic.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void test() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(config.class);
        StatefulService SS = ac.getBean("statefulService", StatefulService.class);
        StatefulService SS2 = ac.getBean("statefulService", StatefulService.class);
        SS.setPrice(1);
        SS2.setPrice(2);
        System.out.println(SS.getPrice());
        System.out.println(SS.getPrice());
        Assertions.assertThat(SS.getPrice()).isNotEqualTo(21);
    }

    @Configuration
    static class config{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
}