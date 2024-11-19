package Spring.Basic.beanfind;

import Spring.Basic.AppConfig;
import Spring.Basic.discount.FixDiscountPolicy;
import Spring.Basic.member.MemberService;
import Spring.Basic.member.MemberServiceImpl;
import Spring.Basic.member.MemoryMemberRepository;
import Spring.Basic.order.OrderService;
import Spring.Basic.order.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ApplicationContextSameBeanFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);

    @Configuration
    static class Config {
        @Bean
        public OrderService OrderService() {
            return new OrderServiceImpl(new FixDiscountPolicy(), new MemberServiceImpl(new MemoryMemberRepository()));
        }

        @Bean
        public OrderService orderService2() {
            return new OrderServiceImpl(new FixDiscountPolicy(), new MemberServiceImpl(new MemoryMemberRepository()));
        }
    }

    @Test
    @DisplayName("같은 타입의 모든 빈을 조회")
    public void findBeanByTypeDuplicate() {
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(OrderService.class));
    }

    @Test
    @DisplayName("같은 타입의 빈이 있을 시 빈 이름을 지정")
    public void findBeanByTypeDuplicate2() {
        OrderService orderService = ac.getBean("orderService2", OrderService.class);
        assertThat(orderService).isInstanceOf(OrderService.class);
    }

    @Test
    @DisplayName("같은 타입의 모든 빈 오류 없이 조회")
    public void findBeanByTypeDuplicate3() {
        Map<String, OrderService> beansOfType = ac.getBeansOfType(OrderService.class);
        for (String string : beansOfType.keySet()) {
            System.out.println("key " + string +"value" + beansOfType.get(string));
        }
        assertThat(beansOfType.size()).isEqualTo(2);
    }
}
