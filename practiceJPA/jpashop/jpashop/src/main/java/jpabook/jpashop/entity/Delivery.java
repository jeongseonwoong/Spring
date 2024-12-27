package jpabook.jpashop.entity;

import jakarta.persistence.*;
import jpabook.jpashop.enums.DeliveryStatus;
import jpabook.jpashop.valuetype.Address;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "DELIVERY")
public class Delivery {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    private DeliveryStatus status;
}
