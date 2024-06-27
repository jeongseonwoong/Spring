package Spring.Basic;

import Spring.Basic.member.Grade;
import Spring.Basic.member.Member;
import Spring.Basic.member.MemberService;
import Spring.Basic.member.MemberServiceImpl;




public class MemberApp {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        Member member = new Member("member1",123L,Grade.VIP);
        memberService.join(member);
        System.out.println(member.getName());
        System.out.println(memberService.findMember(123L).getName());
    }
}
