package com.gingermadfire.security;

import com.gingermadfire.persistence.User;
import com.gingermadfire.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final BadCredentialsException badCredentialsException = new BadCredentialsException(
            "Логин и/или пароль указаны неверно"
    );

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String password = (String) authentication.getCredentials();
        String username = (String) authentication.getPrincipal();
        User user = userRepository.findByUsername(username).orElseThrow(() -> badCredentialsException);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw badCredentialsException;
        }

        return new UsernamePasswordAuthenticationToken(username, password, user.getRole().getAuthorities());
    }

}
