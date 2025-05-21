package OAuth_JWT.oauth_jwt.security.filter;

import OAuth_JWT.oauth_jwt.security.utils.TokenUtils;
import OAuth_JWT.oauth_jwt.security.auth.CustomUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenUtils tokenUtils;

    private final CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = getJwtFromRequest(request);
            if (jwt != null && !jwt.isEmpty() && !jwt.isBlank() && tokenUtils.validateToken(jwt)) {
                String username = tokenUtils.getUsernameFromToken(jwt);
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }catch (Exception e){
            log.info("토큰 인증 오류 ={}",e.getMessage());
        }
        filterChain.doFilter(request,response);
    }

    private String getJwtFromRequest(HttpServletRequest request){
        String tokenWithBearer = request.getHeader("Authorization");
        if(tokenWithBearer.startsWith("Bearer")) {
            return tokenWithBearer.replace("Bearer","");
        }
        return null;
    }
}
