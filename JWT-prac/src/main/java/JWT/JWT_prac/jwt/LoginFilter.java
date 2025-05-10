package JWT.JWT_prac.jwt;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있는데
//이 필터는 /login을 요청해서 username, password를 post로 전송하면
//UsernamePasswordAuthenticationFilter 이게 동작을함. 이거 때문에 지금은 동작안함. .formLogin(AbstractHttpConfigurer::disable) //폼태그를 이용한 로그인 안씀
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
}
