package jpabook.jpashop.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    private Long id;

    @NotEmpty(message = "id값이 입력되지 않음.")
    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;

    public void updateBookForm(Long id, String name, int price, int stockQuantity, String author, String isbn){
        this.id =id;
        this.name =name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }
}
