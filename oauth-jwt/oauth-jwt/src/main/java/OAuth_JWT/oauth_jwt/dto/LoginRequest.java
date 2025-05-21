package OAuth_JWT.oauth_jwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {
    String username;
    String password;
}
