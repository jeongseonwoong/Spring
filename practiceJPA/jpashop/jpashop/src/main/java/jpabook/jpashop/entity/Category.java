package jpabook.jpashop.entity;

import jakarta.persistence.*;
import jpabook.jpashop.entity.item.Item;

import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Item> items;

}
