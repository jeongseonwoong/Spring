package security.OAuth.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import security.OAuth.dto.UserForm;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue
    private UUID id;

    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String provider;

    private String providerId;

    @CreatedDate
    private LocalDateTime createdDate;

    public void assignRole(Role role){
        this.role = role;
    }

    public void bcryptPassword(String bCryptedPassword){
        this.password = bCryptedPassword;
    }

    public User(UserForm userForm){
        this.password = userForm.getPassword();
        this.email = userForm.getEmail();
        this.username = userForm.getUsername();
    }

}
