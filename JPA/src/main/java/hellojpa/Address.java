package hellojpa;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@EqualsAndHashCode
public class Address {
    private String city;
    private String street;
    private String zipcode;
    public Address(){}
    public Address(String city,String street, String zipcode){
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }



}
