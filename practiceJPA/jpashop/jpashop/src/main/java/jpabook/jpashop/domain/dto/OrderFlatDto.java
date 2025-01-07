package jpabook.jpashop.domain.dto;

import jpabook.jpashop.domain.enums.OrderStatus;
import jpabook.jpashop.domain.valuetype.Address;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class OrderFlatDto {

    private final Long orderId;
    private final String name;
    private final LocalDateTime orderDate;
    private final OrderStatus orderStatus;
    private final Address address;

    private final String itemName;
    private final int orderPrice;
    private final int count;
}
