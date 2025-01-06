package jpabook.jpashop.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemDTO {
    private String itemName;
    private Integer orderPrice;
    private Integer count;
}
