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
import security.OAuth.repository.UserRepository;

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
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인 완료 -> code를 리턴(OAuth-client라이브러리) -> AccessToken 요청
        // userRequest 정보 -> loadUser함수 호출 -> 구글로부터 회원 프로필을 받음
        if(userRequest==null) throw new RuntimeException();
        log.info("userRequest.getAttributes: {}", super.loadUser(userRequest).getAttributes());
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getClientId(); // 구글이면 구글
        String providerId = (String) oAuth2User.getAttributes().get("sub");
        String email = (String) oAuth2User.getAttributes().get("email");
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
