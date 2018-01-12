package ubermaster.entity.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import ubermaster.entity.model.User;

import java.util.Collection;

/**
 * Created by stephan on 20.03.16.
 */
public class JwtUser<T extends User> implements UserDetails {

    private Long id;
    private String phone;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean enabled;

    public JwtUser(
            Long id,
            String username,
            String password, Collection<? extends GrantedAuthority> authorities,
            boolean enabled
    ) {
        this.id = id;
        this.phone = username;
        this.password = password;
        this.authorities = authorities ;
        this.enabled = enabled;
    }

    public JwtUser(T user) {
        this.id = user.getObject_id();
        this.phone = user.getPhoneNumber();
        this.password = user.getPassword();
        this.authorities = (AuthorityUtils.commaSeparatedStringToAuthorityList(user.getClassType().toUpperCase()));
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
