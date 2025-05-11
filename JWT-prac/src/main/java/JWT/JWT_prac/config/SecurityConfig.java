package JWT.JWT_prac.config;

import JWT.JWT_prac.filter.TokenAuthenticationFIlter;
import JWT.JWT_prac.filter.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsFilter corsFilter;
    private final CorsConfigurationSource corsSource;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenAuthenticationFIlter tokenAuthenticationFIlter(){
        return new TokenAuthenticationFIlter();
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setFilterProcessesUrl("/api/user/login");
        loginFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        return loginFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) //csrf 비활성화
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션 안씀
            .formLogin(AbstractHttpConfigurer::disable) //폼태그를 이용한 로그인 안씀
            .httpBasic(AbstractHttpConfigurer::disable) // http 로그인 방식 안씀
//            .addFilter(corsFilter) //@CrossOrigin(인증 x), 시큐리티 필터에 등록 인증(o)
            .cors(cors-> cors.configurationSource(corsSource))
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/api/v1/user/**").hasAnyRole("USER","MANAGER","ADMIN")
                    .requestMatchers("/api/1/manager/**").hasAnyRole("MANAGER","ADMIN")
                    .requestMatchers("/api/1/admin/**").hasRole("ADMIN")
                    .anyRequest().permitAll());

        http.addFilterBefore(tokenAuthenticationFIlter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



}
