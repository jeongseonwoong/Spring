package jpabook.jpashop.entity;

import jakarta.persistence.*;
import jpabook.jpashop.valuetype.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Embedded
    @Column(name = "ADDRESS")
    private Address address;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Order> orders;
}
