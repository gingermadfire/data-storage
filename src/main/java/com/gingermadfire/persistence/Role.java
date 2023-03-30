package com.gingermadfire.persistence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum Role {

    USER(Set.of(Permission.USERS_READ)),
    VIP(Set.of(Permission.USERS_READ, Permission.USERS_WRITE)),
    ADMIN(Set.of(Permission.USERS_READ, Permission.USERS_WRITE, Permission.USERS_DELETE, Permission.USERS_PUT));

    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }

}
