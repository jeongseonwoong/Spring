package jpabook.jpashop.domain.entity.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("B")
public class Book extends Item{
    private String author;
    private String isbn;

    public void updateBook(String name, int price, int stockQuantity, String author, String isbn){
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author=author;
        this.isbn=isbn;
    }
}
