package jpabook.jpashop.entity;


import jakarta.persistence.*;
import jpabook.jpashop.entity.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ORDER_ITEM")
public class OrderItem {

    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @Column(name = "ORDERPRICE")
    private Integer orderPrice;

    @Column(name = "COUNT")
    private Integer count;
}
