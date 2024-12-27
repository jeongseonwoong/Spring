package jpabook.jpashop.domain.entity;


import jakarta.persistence.*;
import jpabook.jpashop.domain.entity.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "ORDER_ITEM")
public class OrderItem {

    @GeneratedValue @Id
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @Column(name = "ORDERPRICE")
    private Integer orderPrice;//주문 가격

    @Column(name = "COUNT")
    private Integer count;//주문 수량
}
