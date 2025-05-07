package security.OAuth.config.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import security.OAuth.Entity.Role;
import security.OAuth.Entity.User;
import security.OAuth.config.CustomUserDetails;
import security.OAuth.config.oauth.provider.FacebookUserInfo;
import security.OAuth.config.oauth.provider.GoogleUserInfo;
import security.OAuth.config.oauth.provider.NaverUserInfo;
import security.OAuth.config.oauth.provider.OAuth2UserInfo;
import security.OAuth.repository.UserRepository;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    //구글로부터 받은 userRequest 데이터에 대한 후처리 함수
    //userRequest.getClientRegistration()
    //userRequest.getAccessToken()
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Override
    //해당함수 종료시 @AuthenticationPrincipal이 만들어짐.
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인 완료 -> code를 리턴(OAuth-client라이브러리) -> AccessToken 요청
        // userRequest 정보 -> loadUser함수 호출 -> 구글로부터 회원 프로필을 받음
        if(userRequest==null) throw new RuntimeException();
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            log.info("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")){
            log.info("페이스북 로그인 요청");
            oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        }else{
            throw new OAuth2AuthenticationException("구글과 페이스북 로그인만 지원합니다.");
        }
        String provider = oAuth2UserInfo.getProvider(); // 구글이면 구글
        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String username = provider+"-"+providerId; //google_10974285182916427686
        String password = bCryptPasswordEncoder.encode("가나다라");
        Role role = Role.ROLE_USER;

        User userEntity = userRepository.findByUsername(username);

        if(userEntity==null){
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }
        return new CustomUserDetails(userEntity,oAuth2User.getAttributes());
    }

}
