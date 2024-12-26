package jpql;


import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;

import javax.persistence.*;
import java.util.Collection;
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
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select t from Team t join fetch t.members";
            List<Team> result = em.createQuery(query, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(1)
                    .getResultList();
            for (Team team : result) {
                System.out.println("Team Name = " + team.getName());
                System.out.print("Team Member = ");
                for (Member member : team.getMembers()) {
                    System.out.print(member.getUsername() + " ");
                }
                System.out.println();
            }


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
