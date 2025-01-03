package jpabook.jpashop.api.domain.dto;

import jpabook.jpashop.domain.entity.item.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemDTO {
    private String itemName;
    private Integer orderPrice;
    private Integer count;
}
