package Spring.Basic.singleton;

import Spring.Basic.AppConfig;
import Spring.Basic.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer(){
        AppConfig appConfig = new AppConfig();

        //1. 호출할 때마다 객체를 생성하는지 조회.
        MemberService memberService = appConfig.memberService();

        MemberService memberService2 = appConfig.memberService();

        System.out.println(memberService);
        System.out.println(memberService2);

        Assertions.assertThat(memberService).isNotSameAs(memberService2);

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService3 = ac.getBean("memberService", MemberService.class);
        MemberService memberService4 = ac.getBean("memberService", MemberService.class);
        Assertions.assertThat(memberService3).isSameAs(memberService4);
    }

    @Test
    @DisplayName("싱글톤서비스 테스트")
    void singletonServiceTest(){
        SingletonService singletonService = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();
        //객체가 하나밖에 생성되지 않도록 설계;
        Assertions.assertThat(singletonService).isSameAs(singletonService2);
    }

    @Test
    @DisplayName("spring 컨테이너와 싱클톤")
    void springBeanTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);
        Assertions.assertThat(memberService1).isSameAs(memberService2);
    }
}
