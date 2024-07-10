package Spring.Basic.prototype;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

public class ProtoTypeTest {

    @Test
    public void testPrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoConfiguration.class);
        MyBean myBean1 = ac.getBean("myBean", MyBean.class);
        MyBean myBean2 = ac.getBean("myBean", MyBean.class);
        Assertions.assertThat(myBean1).isNotEqualTo(myBean2);
        ac.close();
    }

    @Test
    void singletonBeanFind(){

    }

    @Configuration
    public static class ProtoConfiguration {

        @Bean
        @Scope("prototype")
        public MyBean myBean(){
            return new MyBean();
        }

    }
}
