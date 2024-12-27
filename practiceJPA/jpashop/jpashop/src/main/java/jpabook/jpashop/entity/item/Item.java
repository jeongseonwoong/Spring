package jpabook.jpashop.entity.item;

import jakarta.persistence.*;
import jpabook.jpashop.entity.Category;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {

    @GeneratedValue
    @Id
    @Column(name = "ITEM_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private int price;

    private int stockQuantity;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<Category> Categories;

}
