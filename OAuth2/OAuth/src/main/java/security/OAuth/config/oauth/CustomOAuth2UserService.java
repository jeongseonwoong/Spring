package security.OAuth.config.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    //구글로부터 받은 userRequest 데이터에 대한 후처리 함수
    //userRequest.getClientRegistration()
    //userRequest.getAccessToken()
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest: {} ",userRequest);
        log.info("userRequest.getClientRegistration: {}", userRequest.getClientRegistration());
        log.info("userRequest.getAccessToken: {}", userRequest.getAccessToken().getTokenValue());
        log.info("userRequest.getAdditionalParameters: {}", userRequest.getAdditionalParameters());
        log.info("userRequest.getAttributes: {}", super.loadUser(userRequest).getAttributes());

        return super.loadUser(userRequest);
    }

}
