package blogProject.DevBlog.Security;

import blogProject.DevBlog.user.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Getter
public class CustomUserDetails implements UserDetails, OAuth2User {

    private final User user;
    private Map<String, Object> attributes;


    public CustomUserDetails(User user) {
        this.user = user;
    }

    public CustomUserDetails(User user, OAuthAttributes oAuthAttributes) {
        this.user = user;
        this.attributes = oAuthAttributes.getAttributes();
    }

    public List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        roles.add(user.getRoleKey());
        return roles;
    }


    @Override
    public Map<String, Object> getAttribute(String name) {
        return attributes;
    }


    @Override
    public String getName() {
        return user.getName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getKey());
        return Collections.singleton(authority);

    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
    public Map<String, Object> getAttributes() {
        return null;
    }


}
