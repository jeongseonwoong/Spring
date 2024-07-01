package Spring.Basic.member;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long,Member> store = new HashMap<Long,Member>();

    @Override
    public void save(Optional<Member> member) {
        member.ifPresent(member1->store.put(member1.getId(),member1));
    }


    @Override
    public Optional<Member> findById(Long id) {
        if(store.containsKey(id)) {
            return Optional.of(store.get(id));
        }
        return Optional.empty();
    }


}
