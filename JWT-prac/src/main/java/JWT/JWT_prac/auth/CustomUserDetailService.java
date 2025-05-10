package JWT.JWT_prac.auth;

import JWT.JWT_prac.entity.User;
import JWT.JWT_prac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// http://localhost:8080/login 요청 시 동작 x. -> 이거 때문에 .formLogin(AbstractHttpConfigurer::disable) //폼태그를 이용한 로그인 안씀
@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        log.info(userEntity.getUsername());
        return new CustomUserDetails(userEntity);
    }
}
