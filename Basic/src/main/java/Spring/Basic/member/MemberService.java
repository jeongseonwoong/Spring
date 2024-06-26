package Spring.Basic.member;

public interface MemberService {
    public void join(Member member);

    public Member findMember(Long id);
}
