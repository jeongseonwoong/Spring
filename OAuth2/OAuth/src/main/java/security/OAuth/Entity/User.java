package security.OAuth.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import security.OAuth.dto.UserForm;

import java.sql.Timestamp;
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

    @Builder
    public User(String username, String password, String email, Role role, String provider, String providerId, LocalDateTime createDate){
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createdDate = createDate;
    }

}
