package jpabook.jpashop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@EqualsAndHashCode
public class Address {

    private String city;
    private String street;
    private String zipcode;

}
