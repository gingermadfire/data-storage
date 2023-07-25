package com.gingermadfire.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Getter
@AllArgsConstructor
public class SecurityUser implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final Set<SimpleGrantedAuthority> authorities;
    private static final boolean IS_ACTIVE = true;

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
}
