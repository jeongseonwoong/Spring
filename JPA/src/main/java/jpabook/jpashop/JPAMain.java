package jpabook.jpashop;





import jpabook.jpashop.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JPAMain {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager em =entityManagerFactory.createEntityManager();
        //code
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try{
            Member member = new Member();
            member.setName("hello");

            em.persist(member);

            em.flush();
            em.clear();

            //
            em.find(Member.class,member.getId());
            System.out.println("findMember.id = " + member.getId());
            System.out.println("findMember.username = " + member.getName());


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