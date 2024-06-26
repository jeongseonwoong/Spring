package Spring.Basic.member;

import java.util.Optional;

public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();



    @Override
    public void join(Member member) {
        memberRepository.save(Optional.ofNullable(member));
    }

    @Override
    public Member findMember(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.orElse(null);
    }
}
