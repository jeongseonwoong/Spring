//package jpabook.jpashop;
//
//
//
//import jpabook.jpashop.domain.Member;
//import jpabook.jpashop.domain.Order;
//import jpabook.jpashop.domain.OrderItem;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityTransaction;
//import javax.persistence.Persistence;
//
//public class JPAMain {
//    public static void main(String[] args) {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
//        EntityManager entityManager =entityManagerFactory.createEntityManager();
//        //code
//        EntityTransaction transaction = entityManager.getTransaction();
//        transaction.begin();
//        try{
//            Order order = new Order();
//            order.addOrderItem(new OrderItem());
//            transaction.commit();
//        } catch (Exception e){
//            transaction.rollback();
//        } finally{
//            entityManager.close();
//        }
//
//        entityManager.close();
//        entityManagerFactory.close();
//    }
//}