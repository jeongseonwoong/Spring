package Spring.Basic.prototype;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
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
        Assertions.assertThat(myBean1).isNotSameAs(myBean2);
        ac.close();
    }

    @Test
    void singletonBeanFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoConfiguration.class);
        MyBean myBean1 = ac.getBean("myBean", MyBean.class);
        MyBean myBean2 = ac.getBean("myBean", MyBean.class);

        //prototype
        Assertions.assertThat(myBean1.add()).isEqualTo(1);
        System.out.println(myBean1.getCount());
        Assertions.assertThat(myBean2.add()).isEqualTo(1);
        System.out.println(myBean2.getCount());

        //singleton
        MyBean2 myBean3 = ac.getBean("myBean2", MyBean2.class);
        MyBean2 myBean4 = ac.getBean("myBean2", MyBean2.class);
        Assertions.assertThat(myBean3.add()).isEqualTo(1);
        System.out.println(myBean3.getCount());
        Assertions.assertThat(myBean4.add()).isEqualTo(2);
        System.out.println(myBean4.getCount());
    }

    @Test
    void prototypeInsideSingleton(){
        //싱글톤 빈 생성시 의존성 주입을 위해 생성된 프로토 타입 빈이 계속해서 쓰임.
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoConfiguration.class);
        NeedMyBean nb = ac.getBean("needMyBean", NeedMyBean.class);
        NeedMyBean nb2 = ac.getBean("needMyBean", NeedMyBean.class);
        nb.myBean.add();
        Assertions.assertThat(nb.myBean.getCount()).isEqualTo(1);
        nb2.myBean.add();
        Assertions.assertThat(nb.myBean.getCount()).isEqualTo(2);
    }

    @Test
    void prototypeInsidePrototype2(){
        //ObjectProvider를 사용해 프로토 타입 빈 계속해서 생성
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoConfiguration.class);
        NeedMyBean2 nb = ac.getBean("needMyBean2", NeedMyBean2.class);
        NeedMyBean2 nb2 = ac.getBean("needMyBean2", NeedMyBean2.class);
        nb.getMyBean().add();
        Assertions.assertThat(nb.getMyBean().getCount()).isEqualTo(0);
        Assertions.assertThat(nb.getMyBean().add()).isEqualTo(1);


    }

    @Configuration
    public static class ProtoConfiguration {

        @Bean
        @Scope("prototype")
        public MyBean myBean(){
            return new MyBean();
        }

        @Bean
        @Scope("singleton")
        public MyBean2 myBean2(){
            return new MyBean2();
        }

        @Bean
        @Scope("singleton")
        public NeedMyBean needMyBean(){
            return new NeedMyBean(myBean());
        }

        @Bean
        @Scope("singleton")
        public NeedMyBean2 needMyBean2(){
            return new NeedMyBean2();
        }
    }
}
