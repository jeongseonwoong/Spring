package jpabook.jpashop.domain.entity.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.entity.Category;
import jpabook.jpashop.domain.entity.CategoryItem;
import jpabook.jpashop.domain.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {

    @GeneratedValue @Id
    @Column(name = "ITEM_ID")
    private Long id;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "PRICE")
    protected int price;

    @Column(name = "STOCK_QUANTITY")
    protected int stockQuantity;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<CategoryItem> categories = new ArrayList<>();

    //==비즈니스 로직 ==/
    /**
     * stock 증가
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
