package jpabook.jpashop.api.domain.dto;

import jpabook.jpashop.domain.entity.Delivery;
import jpabook.jpashop.domain.entity.OrderItem;
import jpabook.jpashop.domain.enums.OrderStatus;
import jpabook.jpashop.domain.valuetype.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrderDTO2 {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDTO> orderItems;

    public OrderDTO2(Long id,String name,LocalDateTime dateTime,OrderStatus orderStatus,Address address,List<OrderItem> orderItems){
        this.orderId = id;
        this.name = name;
        this.orderDate = dateTime;
        this.orderStatus = orderStatus;
        this.address = address;
        List<OrderItemDTO> orderItemDTOS = orderItems.stream().map(orderItem
                        -> new OrderItemDTO(orderItem.getItem().getName(), orderItem.getOrderPrice(), orderItem.getCount()))
                .collect(Collectors.toList());
        this.orderItems = orderItemDTOS;
    }
}