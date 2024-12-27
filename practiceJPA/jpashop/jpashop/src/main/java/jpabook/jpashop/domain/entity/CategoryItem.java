package jpabook.jpashop.domain.entity;

import jakarta.persistence.*;
import jpabook.jpashop.domain.entity.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "CATEGORY_ITEM")
public class CategoryItem {

    @Id
    @GeneratedValue
    @Column(name = "CAGEGORY_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

}
