package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager em =entityManagerFactory.createEntityManager();
        //code
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try{
            Address homeAddress = new Address("Seoul", "abc street", "10-1");

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(homeAddress);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setHomeAddress(homeAddress);
            em.persist(member2);

            Address copyAddress = new Address(homeAddress.getCity(), homeAddress.getStreet(), homeAddress.getZipcode());
            Member member3 = new Member();
            member3.setUsername("member2");
            member3.setHomeAddress(homeAddress);
            em.persist(member3);

            System.out.println(homeAddress.equals(copyAddress));
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
