package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager =entityManagerFactory.createEntityManager();
        //code
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try{
            Team team = new Team();
            team.setName("Team 2");
            entityManager.persist(team);

            Member member = new Member();
            member.setUsername("member2");
            member.setTeam(team);
            entityManager.persist(member);

            Member findMember = entityManager.find(Member.class, member.getId());
            Team team1 = findMember.getTeam();
            System.out.println(team1);
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
        } finally{
            entityManager.close();
        }

        entityManager.close();
        entityManagerFactory.close();
    }
}
