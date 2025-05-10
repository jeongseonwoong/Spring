package JWT.JWT_prac.entity;

import JWT.JWT_prac.enums.Role;
import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import lombok.Getter;


@Entity
@Getter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role roles;
}
