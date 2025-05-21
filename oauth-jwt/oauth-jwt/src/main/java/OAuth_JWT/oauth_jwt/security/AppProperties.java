package OAuth_JWT.oauth_jwt.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;


@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Auth auth = new Auth();
    private final OAuth oauth = new OAuth();


    public Auth getAuth() {return auth;}

    public OAuth getOauth() {return oauth;}

    @Getter
    @Setter
    public static class Auth {
        private String tokenSecret;
        private long tokenExpirationMsec;
    }

    @Getter
    @Setter
    public static class OAuth {
        private List<String> authorizedRedirectUris = new ArrayList<>();
    }
}
