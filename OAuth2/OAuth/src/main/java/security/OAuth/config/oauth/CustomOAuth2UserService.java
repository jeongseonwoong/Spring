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
        // 구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인 완료 -> code를 리턴(OAuth-client라이브러리) -> AccessToken 요청
        // userRequest 정보 -> loadUser함수 호출 -> 구글로부터 회원 프로필을 받음

        OAuth2User oAuth2User = super.loadUser(userRequest);
        return super.loadUser(userRequest);
    }

}
