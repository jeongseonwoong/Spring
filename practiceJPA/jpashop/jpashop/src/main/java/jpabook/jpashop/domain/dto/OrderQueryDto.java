package jpabook.jpashop.domain.dto;

import jpabook.jpashop.domain.enums.OrderStatus;
import jpabook.jpashop.domain.valuetype.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Data
public class OrderQueryDto {
    private final Long orderId;
    private final String name;
    private final LocalDateTime orderDate;
    private final OrderStatus orderStatus;
    private final Address address;
    private List<OrderItemQueryDto> orderItems;

}
