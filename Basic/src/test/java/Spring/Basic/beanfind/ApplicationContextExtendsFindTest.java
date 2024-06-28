package Spring.Basic.beanfind;

import Spring.Basic.discount.DiscountPolicy;
import Spring.Basic.discount.FixDiscountPolicy;
import Spring.Basic.discount.RateDiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ApplicationContextExtendsFindTest {


    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);

    @Configuration
    static class Config {

        @Bean
        public DiscountPolicy discountPolicy() {
            return new FixDiscountPolicy();
        }

        @Bean
        public DiscountPolicy discountPolicy2() {
            return new RateDiscountPolicy();
        }
    }

    @Test
    @DisplayName("부모객체의 하위객체가 두개 이상 있을 때 오류 발생")
    public void TwoChildNode()
    {
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class,()->ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("부모객체의 하위객체가 두개 이상있을 때 이름으로 구분")
    public void TwoChildNode2()
    {
        assertThat(ac.getBean("discountPolicy2",DiscountPolicy.class))
                .isInstanceOf(DiscountPolicy.class);
    }

    @Test
    @DisplayName("하위 노드로 바로 조회")
    public void TwoChildNode3()
    {
        assertThat(ac.getBean(FixDiscountPolicy.class)).isInstanceOf(DiscountPolicy.class);
    }

    @Test
    @DisplayName("부모클래스에 해당되는 타입들 모두 조회하기")
    public void TwoChildNode4()
    {
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);
        for(String key : beansOfType.keySet())
        {
            System.out.println("key = " + key + ", bean = " + beansOfType.get(key));
        }
    }

    @Test
    @DisplayName("Object 타입으로 모두 조회하기")
    public void TwoChildNode5()
    {
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        assertThat(beansOfType.size()).isNotEqualTo(2); //스프링 빈에 등록된 모든 객체가 다튀어나오기 때문에 2개x

        int count =0;
        for(String key : beansOfType.keySet())
        {

            System.out.println("key = " + key + ", bean = " + beansOfType.get(key).getClass());
        }

    }
}
