package jpabook.jpashop.api.domain.dto;

import jpabook.jpashop.domain.entity.Order;
import jpabook.jpashop.domain.enums.OrderStatus;
import jpabook.jpashop.domain.valuetype.Address;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleOrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public SimpleOrderDto(Order order){
        orderId =order.getId();
        name = order.getMember().getName();
        orderDate = order.getOrderDate();
        orderStatus = order.getOrderStatus();
        address = order.getDelivery().getAddress();
    }
}
