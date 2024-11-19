package personal.practice.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personal.practice.domain.Member;
import personal.practice.repository.MemberRepository;
import personal.practice.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

@Transactional //JPA 사용 시 꼭 필요
public class MemberService {
    private final MemberRepository memberRepository;


    MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member) {

        memberRepository.findByName(member.getName())
        .ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        });
        memberRepository.save(member);
        return member.getId();
    }


    public List<Member> findMember()
    {
        return memberRepository.findAll();
    }

    public Optional<Member> findMemberById(Long id) {
        return memberRepository.findById(id);
    }


    public Optional<Member> findOne(Long id) {
        return memberRepository.findById(id);
    }
}
