package jpabook.jpashop.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateMemberRequest {
    @NotEmpty
    private String name;
}
