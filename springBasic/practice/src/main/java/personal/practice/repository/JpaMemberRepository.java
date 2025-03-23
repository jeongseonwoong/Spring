package personal.practice.repository;

import jakarta.persistence.EntityManager;
import personal.practice.domain.Member;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em; //jpa는 엔티티 메니저로 모든게 동작된다.

    public JpaMemberRepository(EntityManager em) {//spring container에서 자동으로 등록된  Entity 가져오기
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name",Member.class).setParameter("name",name).getResultList();
        return result.stream().findFirst();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member as m", Member.class).getResultList();
    }
}
