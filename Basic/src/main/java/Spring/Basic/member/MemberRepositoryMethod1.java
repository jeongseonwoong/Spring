package Spring.Basic.member;

import java.util.Optional;

public class MemberRepositoryMethod1 implements MemberRepository {
    @Override
    public void save(Optional<Member> member) {

    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.empty();
    }
}
