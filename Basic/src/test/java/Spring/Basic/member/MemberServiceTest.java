package Spring.Basic.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    MemberService memberService = new MemberServiceImpl();

    @Test
    void join(){
        //given
        Member member = new Member("TestMember",10L,Grade.VIP);

        //when
        memberService.join(member);

        //then
        Assertions.assertThat(memberService.findMember(10L)).isEqualTo(member);
    }
}
