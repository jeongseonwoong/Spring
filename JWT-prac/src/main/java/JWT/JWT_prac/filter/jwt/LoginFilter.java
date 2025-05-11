package JWT.JWT_prac.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있는데
//이 필터는 /login을 요청해서 username, password를 post로 전송하면
//UsernamePasswordAuthenticationFilter 이게 동작을함. 이거 때문에 지금은 동작안함. .formLogin(AbstractHttpConfigurer::disable) //폼태그를 이용한 로그인 안씀
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    //login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //1. username, password 받아서
        //2. 정상인지 로그인 시도를 해본다. authenticationManager로 로그인 시도를 하면 CustomUserDetailsService가 호출.
        //3. 그럼 loadUserByUsername()이 실행됨.
        //4. 정상적으로 CustomUserDetails가 리턴이 되면 CustomUserDetails를 세션에 담고 (세션에 담는 이유, 권한 관리 USER, MANAGER, ADMIN을 위해서, JWT에 권한 넣어주면 안해줘도 된다네요)
        //5. 마지막으로 JWT 토큰을 만들어서 응답해주면됨.
        return super.attemptAuthentication(request, response);
    }
}
