package Spring.Basic.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void testBeanLifeCycle() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
        NetworkClient nc = ac.getBean("networkClient", NetworkClient.class);
        nc.connect();
        ac.close();
    }

    @Configuration
    static class Config {

        @Bean(initMethod = "init",destroyMethod = "close")
        public NetworkClient networkClient() {
            NetworkClient net = new NetworkClient();
            net.setUrl("http://localhost:8080");
            return net;
        }
    }
}
