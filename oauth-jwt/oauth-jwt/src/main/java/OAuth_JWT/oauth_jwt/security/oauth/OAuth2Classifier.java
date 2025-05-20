package OAuth_JWT.oauth_jwt.security.oauth;

import java.util.Map;

public class OAuth2Classifier {

    private OAuth2Classifier(){};

    public static OAuth2UserInfo classifyOAuth2Register(String registrationId, Map<String,Object> attributes ) throws IllegalAccessException {
        if(registrationId.equalsIgnoreCase("google")){
            return new GoogleOAuth2UserInfo(attributes);
        }else if(registrationId.equalsIgnoreCase("facebook")){
            return new FacebookOAuth2UserInfo(attributes);
        }else if(registrationId.equalsIgnoreCase("naver")){
            return new NaverOAuth2UserInfo((Map)attributes.get("response"));
        }
        throw new IllegalAccessException("구글, 페이스북, 네이버 소셜 로그인만 가능합니다.");
    }

}
