package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void save(){
        //given
        Member member = new Member("hello",20);

        //when
        Member savedMember = memberRepository.save(member);

        //then
        assertThat(memberRepository.findById(savedMember.getId())).isEqualTo(member);
    }

    @Test
    void findAll(){
        //given
        Member member = new Member("member1",20);
        Member member2 = new Member("member2",20);


        //when
        memberRepository.save(member);
        memberRepository.save(member2);

        //then
        assertThat(memberRepository.findAll()).contains(member);
        assertThat(memberRepository.findAll()).contains(member2);
        assertThat(memberRepository.findAll()).size().isEqualTo(2);
    }
}
