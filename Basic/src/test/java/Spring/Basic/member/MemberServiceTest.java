package Spring.Basic.member;

import Spring.Basic.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    MemberService memberService;

    @BeforeEach
    public void setUp() {
        AppConfig appconfig = new AppConfig();
        memberService = appconfig.memberService();
    }

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
