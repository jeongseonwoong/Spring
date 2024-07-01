package Spring.Basic.singleton;

import Spring.Basic.AppConfig;
import Spring.Basic.member.MemberServiceImpl;
import Spring.Basic.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppConfigFeature {


    @Test
    @DisplayName("Config의 기능: 객체가 하나만 생성되도록 해줌")
    public void testConfig() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        OrderServiceImpl os = context.getBean("orderService", OrderServiceImpl.class);

        MemberServiceImpl memberService = context.getBean("memberService", MemberServiceImpl.class);
        System.out.println(os.findMemberService());
        System.out.println(memberService);
        Assertions.assertThat(os.findMemberService()).isSameAs(memberService);
    }
}
