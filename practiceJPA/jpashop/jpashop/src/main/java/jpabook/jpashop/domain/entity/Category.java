package jpabook.jpashop.domain.entity;

import jakarta.persistence.*;
import jpabook.jpashop.domain.entity.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<CategoryItem> items = new ArrayList<>();

    //==연관관계 메서드 ==//
    public void addChild(Category child){
        this.child.add(child);
        child.setParent(this);
    }

    public void addItem(CategoryItem item){
        this.items.add(item);
        item.setCategory(this);
    }

}
