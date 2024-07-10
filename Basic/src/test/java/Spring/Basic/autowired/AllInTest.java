package Spring.Basic.autowired;

import Spring.Basic.AppConfig;
import Spring.Basic.AutoAppConfig;
import Spring.Basic.discount.DiscountPolicy;
import Spring.Basic.member.Grade;
import Spring.Basic.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

public class AllInTest {

    @Test
    void findAllBean(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService ds = ac.getBean(DiscountService.class);
        Member member = new Member("name",1L, Grade.VIP);
        String fx = "fixDiscountPolicy";
        String Rt = "rateDiscountPolicy";


        Assertions.assertThat(ds.discount(member,1000,fx)).isEqualTo(1000);
        Assertions.assertThat(ds.discount(member,1000,Rt)).isEqualTo(100);
    }

    static class DiscountService{
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policyList;

        DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policyList) {
            this.policyMap = policyMap;

            this.policyList = policyList;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policyList = " + policyList);
        }

        int discount(Member member,int price, String DiscountPolicy)
        {
            Spring.Basic.discount.DiscountPolicy dp = policyMap.get(DiscountPolicy);
            return dp.discount(member, price);
        }


    }

}
