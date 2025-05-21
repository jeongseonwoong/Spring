package OAuth_JWT.oauth_jwt.security;

import OAuth_JWT.oauth_jwt.security.auth.CustomUserDetailService;
import OAuth_JWT.oauth_jwt.security.filter.LoginFilter;
import OAuth_JWT.oauth_jwt.security.filter.TokenAuthenticationFilter;
import OAuth_JWT.oauth_jwt.security.handler.LoginFailureHandler;
import OAuth_JWT.oauth_jwt.security.handler.LoginSuccessHandler;
import OAuth_JWT.oauth_jwt.security.handler.OAuth2AuthenticationFailureHandler;
import OAuth_JWT.oauth_jwt.security.handler.OAuth2AuthenticationSuccessHandler;
import OAuth_JWT.oauth_jwt.security.oauth.CustomOAuth2UserService;
import OAuth_JWT.oauth_jwt.security.utils.HttpCookieOAuth2Auth2AuthorizationRequestRepository;
import OAuth_JWT.oauth_jwt.security.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String ADMIN = "ADMIN";
    private static final String MANAGER = "MANAGER";
    private static final String USER = "USER";

    private final CustomUserDetailService customUserDetailService;

    private final CustomOAuth2UserService customOAuth2UserService;

    private final LoginSuccessHandler loginSuccessHandler;

    private final LoginFailureHandler loginFailureHandler;

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    private final TokenUtils tokenUtils;

    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenUtils, customUserDetailService);
    }

    @Bean
    public LoginFilter loginFilter(AuthenticationManager authenticationManager) {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setFilterProcessesUrl("/api/login");
        loginFilter.setAuthenticationManager(authenticationManager);
        loginFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        loginFilter.setAuthenticationFailureHandler(loginFailureHandler);
        return loginFilter;
    }

    public HttpCookieOAuth2Auth2AuthorizationRequestRepository cookieOAuth2RequestRepository() {
        return new HttpCookieOAuth2Auth2AuthorizationRequestRepository();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(c -> c.configurationSource(corsConfigurationSource))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(eh -> eh.authenticationEntryPoint(new ExceptionEntryPoint()))
                .oauth2Login(oauth -> oauth
                        .authorizationEndpoint(aep -> aep
                                .baseUri("/api/oauth2/authorize") // 클라이언트가 로그인 요청할 때 사용할 시작 URL을 지정
                                .authorizationRequestRepository(cookieOAuth2RequestRepository()) //이 시점에서 saveAuthorizationRequest() 호출됨. 즉, OAuth2AuthorizationRequest + redirect_uri 를 쿠키에 저장함
                        )
                        .redirectionEndpoint(rep -> rep
                                .baseUri("/api/oauth2/callback/*")) //OAuth2 인증 제공자가 로그인 성공 후 리다이렉트 해주는 콜백 주소 설정
                        .userInfoEndpoint(uep -> uep.userService(customOAuth2UserService)) //사용자 정보를 가져올 때 사용할 서비스 설정
                        .successHandler(oAuth2AuthenticationSuccessHandler) // OAuth2 로그인 성공 시 실행될 핸들러 여기서 tokenUtils.createToken() 으로 JWT 생성하고 redirect URI로 token 쿼리 파라미터 포함시켜서 리다이렉트
                        .failureHandler(oAuth2AuthenticationFailureHandler)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,"api/admin/**")
                        .hasRole(ADMIN)
                        .requestMatchers(HttpMethod.POST,"api/admin/**")
                        .hasRole(ADMIN)
                        .requestMatchers(HttpMethod.DELETE,"api/admin/**")
                        .hasRole(ADMIN)
                        .requestMatchers(HttpMethod.PATCH,"api/admin/**")
                        .hasRole(ADMIN)
                        .requestMatchers(HttpMethod.GET,"api/manager/**")
                        .hasRole(MANAGER)
                        .requestMatchers(HttpMethod.POST,"api/manager/**")
                        .hasRole(MANAGER)
                        .requestMatchers(HttpMethod.DELETE,"api/manager/**")
                        .hasRole(MANAGER)
                        .requestMatchers(HttpMethod.PATCH,"api/manager/**")
                        .hasRole(MANAGER)
                        .requestMatchers(HttpMethod.GET,"api/user/**")
                        .hasRole(USER)
                        .requestMatchers(HttpMethod.POST,"api/user/**")
                        .hasRole(USER)
                        .requestMatchers(HttpMethod.DELETE,"api/user/**")
                        .hasRole(USER)
                        .requestMatchers(HttpMethod.PATCH,"api/user/**")
                        .hasRole(USER)
                        .anyRequest()
                        .permitAll()
                );

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(loginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
