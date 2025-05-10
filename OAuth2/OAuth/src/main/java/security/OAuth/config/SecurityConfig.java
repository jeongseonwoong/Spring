package security.OAuth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import security.OAuth.config.auth.CustomUserDetailService;
import security.OAuth.config.oauth.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity // 활성화 <- 우리가 작성할 스프링 시큐리티 필터가 스프링 필터 체인에 등록이 된다.
/**
 * Spring Security 5.7 이후부터는
 * 🔹 **WebSecurityConfigurerAdapter**가 deprecated 되었고
 * 🔹 대신 @Bean SecurityFilterChain을 등록하는 방식으로 바뀌었어.
 *
 * Spring Boot 2.7+에서는 자동으로 @EnableWebSecurity를 붙인 효과가 활성화되기 때문에,
 * SecurityFilterChain만 Bean으로 정의하면 Spring Security가 내부적으로 알아서 스프링 필터체인에 등록해줘.
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //secured 어노테이션 활성화
@RequiredArgsConstructor
public class SecurityConfig{

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/user").authenticated() // 인증이 완료되야만 접근 가능
                .requestMatchers("/manager").hasAnyRole("ADMIN", "MANAGER") // /manager는 manager나 admin만 접근 가능
                .requestMatchers("/admin").hasRole("ADMIN") // /admin은 admin만 접근 가능
                .anyRequest().permitAll()// 나머지 uri는 권한이 없어도 접근 가능하도록 설정.
                ).csrf(
                        org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer::disable
                ).logout(l -> l
                        .logoutUrl("/logout") //로그아웃 처리 url(기본값은 POST /logout)
                        .logoutSuccessUrl("/loginForm") //로그아웃 성공 시 리다이렉트 할 경로
                        .invalidateHttpSession(true) //세션 무효화
                        .clearAuthentication(true) //Authentication 객체 클리어
                        .deleteCookies("JSESSIONID") //삭제할 쿠키 이름들
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) //(선택) GET 요청으로도 로그아웃 허용
                ).formLogin(form -> form // 권한이 없는 페이지에 access를 요청할 때 로그인 페이지로 이동하도록 하기.
                .loginPage("/loginForm") //로그인 페이지 url
                .loginProcessingUrl("/login") //로그인 진행 URL
                .defaultSuccessUrl("/") //성공 시 redirect할 url
                ).oauth2Login(oauth2 -> oauth2
                .loginPage("/loginForm") //폼 로그인과 동일한 커스텀 로그인 페이지 사용
                .defaultSuccessUrl("/") // 성공 시 redirect할 url
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(customOAuth2UserService)
                )
        );

        return http.build();
    }


}
