package OAuth_JWT.oauth_jwt.security.oauth;

import OAuth_JWT.oauth_jwt.entity.User;
import OAuth_JWT.oauth_jwt.enums.Role;
import OAuth_JWT.oauth_jwt.repository.UserRepository;
import OAuth_JWT.oauth_jwt.security.CustomUserPrincipal;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            OAuth2UserInfo oAuth2UserInfo = OAuth2Classifier.classifyOAuth2Register(userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

            if(StringUtils.isEmpty(oAuth2UserInfo.getEmail()))
                throw new OAuth2AuthenticationException("OAuth2 provider에 해당하는 이메일이 없습니다.");

            User foundUser = userRepository.findByUsername(oAuth2UserInfo.getEmail());

            if(foundUser == null){
                User newUser = registerNewUser(oAuth2UserInfo);
                return CustomUserPrincipal.create(newUser,oAuth2User.getAttributes());
            }else{
                if(!foundUser.getProvider().equalsIgnoreCase(oAuth2UserInfo.getProvider())){
                    throw new OAuth2AuthenticationException(oAuth2UserInfo.getProvider() +"에서 제공하는 이메일을" +
                            "이미" + foundUser.getProvider() + "에서 사용 중 입니다.");
                }
                return CustomUserPrincipal.create(foundUser,oAuth2UserInfo.getAttributes());
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private User registerNewUser(OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        String password = oAuth2UserInfo.getProvider() + oAuth2UserInfo.getId();
        user.registerUser(oAuth2UserInfo.getEmail(),password, oAuth2UserInfo.getName(), Role.ROLE_USER,
                oAuth2UserInfo.getEmail(), oAuth2UserInfo.getProvider());
        userRepository.save(user);
        return user;
    }

}
