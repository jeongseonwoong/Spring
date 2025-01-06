package jpabook.jpashop.domain.dto;

import jpabook.jpashop.domain.entity.Delivery;
import jpabook.jpashop.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class OrderDTO {
    private Delivery delivery;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;

}
