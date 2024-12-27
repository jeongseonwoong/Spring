package jpabook.jpashop.entity.item;

import jakarta.persistence.Entity;

@Entity
public class Book extends Item{
    private String author;
    private String isbn;

}
