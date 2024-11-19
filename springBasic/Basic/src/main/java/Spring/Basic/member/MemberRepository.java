package Spring.Basic.member;

import java.util.Optional;

public interface MemberRepository {

    void save(Optional<Member> member);

    Optional<Member> findById(Long id);
}
