package JWT.JWT_prac.filter.jwt;

import JWT.JWT_prac.auth.CustomUserDetails;
import JWT.JWT_prac.dto.LoginRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있는데
//이 필터는 /login을 요청해서 username, password를 post로 전송하면
//UsernamePasswordAuthenticationFilter 이게 동작을함. 이거 때문에 지금은 동작안함. .formLogin(AbstractHttpConfigurer::disable) //폼태그를 이용한 로그인 안씀
@RequiredArgsConstructor
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    //login 요청을 하면 로그인 시도를 위해서 실행되는 함수

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        LoginRequest loginRequest;
        try {
            //1. username, password 받아서
            loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        log.info("user= {},{}", loginRequest.getUsername(), loginRequest.getPassword());
        String username = loginRequest.getUsername();;
        String password = loginRequest.getPassword();

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        //토큰 생성
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        setDetails(request,authRequest);
        //2. 정상인지 로그인 시도를 해본다. authenticationManager로 로그인 시도를 하면 CustomUserDetailsService가 호출.
        //3. 그럼 loadUserByUsername()이 실행됨.
        // this.getAuthenticationManager().authenticate(authRequest)가 실행되면서 CustomUserService의 loadUserBysername()함수가 실행됨.
        Authentication authentication = this.getAuthenticationManager().authenticate(authRequest);

        //5. 마지막으로 JWT 토큰을 만들어서 응답해주면됨.
        return authentication;
    }


    //attemptAuthentication이 정상적으로 실행되었을 때 실행. 여기서 JWT 토큰을 만들어서 response;
    //Hash 암호 방식
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
        String jwtToken = JWT.create()
                .withSubject(customUserDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))
                .withClaim("id", customUserDetails.getId())
                .withClaim("username",customUserDetails.getUsername())
                .sign(Algorithm.HMAC512("abcd"));
        log.info("successfulAuthentication 실행됨. token={}",jwtToken);

        response.addHeader("Authorization","Bearer " + jwtToken);
    }
}
