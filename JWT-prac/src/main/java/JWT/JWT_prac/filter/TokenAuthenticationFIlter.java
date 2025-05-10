package JWT.JWT_prac.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;

@Slf4j
public class TokenAuthenticationFIlter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, AuthenticationException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //id, pw가 정상적으로 들어와서 로그인이 완료 되면 토큰을 만들어주고 그걸 응답으로 준다.
        //이후, 요청할 때마다 header에 Authorization에 value값으로 토큰을 가져오면 가져온 토큰을 validate(RSA, HS256)해준다.
        if(request.getMethod().equals("POST")){
            log.info("POST 요청됨");
            String headerAuth = request.getHeader("Authorization");
            log.info(headerAuth);

            if(!headerAuth.equals("token"))
                throw new AuthenticationException("토큰인증이 안됐습니다.");
        }
        filterChain.doFilter(request,response);
    }
}
