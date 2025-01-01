package jpabook.jpashop.api.domain.dto;

import jpabook.jpashop.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MemberDTO {
    private String name;
}
