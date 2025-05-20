package OAuth_JWT.oauth_jwt.entity;

import OAuth_JWT.oauth_jwt.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class User {

    @Id @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String name;

    private String email;

    private String provider;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public void registerUser(String username, String password, String name, Role role, String email, String provider){
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.email = email;
        this. provider =provider;
    }


}
