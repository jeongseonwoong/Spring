package OAuth_JWT.oauth_jwt.security.auth;

import OAuth_JWT.oauth_jwt.entity.User;
import OAuth_JWT.oauth_jwt.repository.UserRepository;
import OAuth_JWT.oauth_jwt.security.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipal;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user==null) throw new UsernameNotFoundException("가입된 정보가 존재하지 않습니다.");
        return CustomUserPrincipal.create(user);
    }
}
