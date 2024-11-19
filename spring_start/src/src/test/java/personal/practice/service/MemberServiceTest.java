package personal.practice.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import personal.practice.domain.Member;
import personal.practice.repository.MemoryMemberRepository;
import personal.practice.repository.MemoryMemberRepositoryTest;
import static org.assertj.core.api.Assertions.*;


class MemberServiceTest {

    MemberService memberService;


    @BeforeEach
    public void setUp()
    {
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");
        //when
        Long saveId=memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복회원예외(){
        Member member = new Member();
        member.setName("hello");
        Member member1=new Member();
        member1.setName("hello");
        Long saveId=memberService.join(member);
        try {
            Long saveId1=memberService.join(member1);
        }
        catch (IllegalStateException e) {
           assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
        }

    }

    @Test
    void findMember() {
    }

    @Test
    void findMemberById() {
    }

}