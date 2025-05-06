package security.OAuth.config;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import security.OAuth.Entity.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

// 시큐리티가 /login uri요청을 낚아채서 로그인을 진행시킨다.
// 로그인이 완료가 되면 시큐리티 session을 만들어주는데 (Security ContextHolder라는 key값에 session 정보를 저장함.)
// 시큐리티 세션에 들어갈 수 있는 Object는 정해져있음. (Authentication 객체)
// Authentication 안에 User 정보가 있어야됨.
// User오브젝트 타입을 UserDetails 타입으로 바꿔야함
// Security Session => Authentication => UserDetails(CustomUserDetails)

@Getter
public class CustomUserDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;

    //일반 로그인
    private CustomUserDetails(User user){this.user = user;}

    // OAuth 로그인
    public CustomUserDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }


    public static CustomUserDetails create(User user){
        return new CustomUserDetails(user);
    }

    // 해당 User의 권한을 리턴하는 곳!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes(){
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }
}
