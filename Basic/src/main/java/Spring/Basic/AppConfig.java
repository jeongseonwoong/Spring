package Spring.Basic;

import Spring.Basic.discount.DiscountPolicy;
import Spring.Basic.discount.FixDiscountPolicy;
import Spring.Basic.discount.RateDiscountPolicy;
import Spring.Basic.member.MemberRepository;
import Spring.Basic.member.MemberService;
import Spring.Basic.member.MemberServiceImpl;
import Spring.Basic.member.MemoryMemberRepository;
import Spring.Basic.order.OrderService;
import Spring.Basic.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService()
    {
        System.out.println("AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService()
    {
        System.out.println("AppConfig.orderService");
        return new OrderServiceImpl(discountPolicy(), memberService());
    }

    @Bean
    public MemberRepository memberRepository()
    {
        System.out.println("AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy()
    {
        System.out.println("AppConfig.discountPolicy");
        return new RateDiscountPolicy();
    }
}
