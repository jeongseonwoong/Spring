package jpabook.jpashop.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberResult<T> {
    private int size;
    private  T data;
}
