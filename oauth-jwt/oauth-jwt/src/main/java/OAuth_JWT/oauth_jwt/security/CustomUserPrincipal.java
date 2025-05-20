package OAuth_JWT.oauth_jwt.security;

import OAuth_JWT.oauth_jwt.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class CustomUserPrincipal implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;

    CustomUserPrincipal(User user) {
        this.user = user;
    }

    public static CustomUserPrincipal create(User user) {
        return new CustomUserPrincipal(user);
    }

    public static CustomUserPrincipal create(User user, Map<String, Object> attributes) {
        CustomUserPrincipal customUserPrincipal = CustomUserPrincipal.create(user);
        customUserPrincipal.setAttributes(attributes);
        return customUserPrincipal;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return user.getName();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
    }

}
