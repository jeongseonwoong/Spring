package jpabook.jpashop.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.valuetype.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "MEMBER")
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Embedded
    @Column(name = "ADDRESS")
    private Address address;


    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    //==연관관계 편의 메서드==//
    public void addOrder(Order order){
        orders.add(order);
        order.setMember(this);
    }

    //== 회원가입을 위한 멤버값 설정 메서드 ==//
    public void updateValueForRegister(String name){
        this.name = name;
    }

    //==회원 정보 변경 시 이름 변경 메서드 ==//
    public void updateValueForModify(String name){
        this.name = name;
    }

}
