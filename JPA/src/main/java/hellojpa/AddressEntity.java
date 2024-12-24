package hellojpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "ADDRESS")
public class AddressEntity {

    @Id @GeneratedValue
    private Long id;

    private Address address;

    public AddressEntity(Address address){
        this.address = address;
    }

    public AddressEntity(String city,String street, String zipcode){
        address = new Address(city,street,zipcode);
    }

    public AddressEntity(){

    }

}
