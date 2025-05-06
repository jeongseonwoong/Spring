package security.OAuth.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import security.OAuth.Entity.User;
import security.OAuth.config.CustomUserDetails;
import security.OAuth.repository.UserRepository;

// 시큐리티 설정에서 loginProcessingUrl("/login");으로 걸어놨기 때문에
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IOC 되어있는 loadUserByUsername 함수가 실행
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    //시큐리티 session = Autnetication를 가지고 있고 Authentication = UserDetails 상태인데
    //여기서 UserDetails를 반환하면 Authentication에 UserDetails 정보가 들어간다. Session(Authentication(UserDetails))

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("유저를 찾을 수 없습니다. username: "+ username);
        }
        return CustomUserDetails.create(user);
    }
}
