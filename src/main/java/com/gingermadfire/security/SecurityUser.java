package com.gingermadfire.security;

import com.gingermadfire.persistence.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@AllArgsConstructor

public class SecurityUser implements UserDetails {

    private final String username;
    private final String password;
    private final Set<SimpleGrantedAuthority> authorities;
    private static final boolean IS_ACTIVE = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return IS_ACTIVE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return IS_ACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return IS_ACTIVE;
    }

    @Override
    public boolean isEnabled() {
        return IS_ACTIVE;
    }

    public static UserDetails fromUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                IS_ACTIVE,
                IS_ACTIVE,
                IS_ACTIVE,
                IS_ACTIVE,
                user.getRole().getAuthorities());
    }
}
