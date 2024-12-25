package jpql;


import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

public class JPAMain {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager em =entityManagerFactory.createEntityManager();
        //code
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try{
            Team team = new Team();
            team.setName("team1");
            em.persist(team);

            Member member = new Member();
            member.setUsername("username");
            member.setAge(10);
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            String query = "select mm.age, mm.username from (select m.age, m.username from Member m) as mm";
            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();

            Member member1 = result.get(0);
            System.out.println("member1.getUsername() = " + member1.getUsername());

            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
        } finally{
            em.close();
        }
        em.close();
        entityManagerFactory.close();
    }
}
