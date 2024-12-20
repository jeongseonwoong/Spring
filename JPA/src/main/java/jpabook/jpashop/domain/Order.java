//package jpabook.jpashop.domain;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "ORDERS")
//public class Order {
//
//    @Id @GeneratedValue
//    @Column(name = "ORDER_ID")
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "MEMBER_ID")
//    private Member member;
//
//    private LocalDateTime orderDate;
//
//    @Enumerated(EnumType.STRING)
//    private OrderStatus status;
//
//    @OneToMany(mappedBy = "order")
//    private List<OrderItem> orderItems = new ArrayList<OrderItem>();
//
//    public void addOrderItem(OrderItem orderItem) {
//        orderItems.add(orderItem);
//        orderItem.setOrder(this);
//    }
//}
