package security.OAuth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity // 활성화 <- 우리가 작성할 스프링 시큐리티 필터가 스프링 필터 체인에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //secured 어노테이션 활성화
public class SecurityConfig{

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

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
                ).formLogin(form -> form
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") //
                .defaultSuccessUrl("/")
                ); // 권한이 없는 페이지에 access를 요청할 때 로그인 페이지로 이동하도록 하기.

        return http.build();
    }


}
