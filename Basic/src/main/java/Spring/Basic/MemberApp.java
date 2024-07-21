package Spring.Basic;

import Spring.Basic.member.Grade;
import Spring.Basic.member.Member;
import Spring.Basic.member.MemberService;
import Spring.Basic.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MemberApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();
        ApplicationContext context = new AnnotationConfigApplicationContext(AutoAppConfig.class); //appConfig에 있는 빈들을 spring 컨테이너에 넣어서 관리해줌.
        MemberService memberService = context.getBean("memberService"/*이름*/, MemberService.class/*타입*/);// spring 컨테이너에서 memberSerivice를 가져옴

        Member member = new Member("member1",123L,Grade.VIP);
        memberService.join(member);
        System.out.println(member.getName());
        System.out.println(memberService.findMember(123L).getName());
    }
}
