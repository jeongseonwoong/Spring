package OAuth_JWT.oauth_jwt.security.utils;

import OAuth_JWT.oauth_jwt.security.AppProperties;
import OAuth_JWT.oauth_jwt.security.CustomUserPrincipal;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenUtils {

    private final AppProperties appProperties;

    public String createToken(Authentication authentication) {
        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

        return JWT.create()
                .withSubject(customUserPrincipal.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(Algorithm.HMAC512(appProperties.getAuth().getTokenSecret()));
    }

    public String getUsernameFromToken(String token){
        return JWT.decode(token).getSubject();
    }

    public boolean validateToken(String authToken){
        try{
            Algorithm algorithm = Algorithm.HMAC512(appProperties.getAuth().getTokenSecret());
            JWT.require(algorithm).build().verify(authToken);
            return true;
        }catch (JWTVerificationException e){
            return false;
        }
    }

}
