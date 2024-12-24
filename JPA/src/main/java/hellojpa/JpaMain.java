package hellojpa;

import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager em =entityManagerFactory.createEntityManager();
        //code
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try{
            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity","street","10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

//            member.getAddressHistory().add(new Address("old1","street","1000"));
//            member.getAddressHistory().add(new Address("old2","street","2000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=======================");
            Member findMember = em.find(Member.class, member.getId());

            //homeCity -> newCity

//            Address a= findMember.getHomeAddress();
//            findMember.setHomeAddress(new Address("newCity",a.getStreet(),a.getZipcode()));

            //치킨 -> 한식
//            findMember.getFavoriteFoods().remove("치킨");
//            findMember.getFavoriteFoods().add("한식");

            findMember.getAddressHistory().remove(new AddressEntity("newCity1","street","2000"));
            findMember.getAddressHistory().add(new AddressEntity("newCity1","street","2000"));

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
