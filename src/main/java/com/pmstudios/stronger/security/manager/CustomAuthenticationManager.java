package com.pmstudios.stronger.security.manager;

import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

//@Component
@AllArgsConstructor
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String inputPassword = authentication.getCredentials().toString();

        User user = userService.getUserByEmail(email);
        String dbPassword = user.getPassword();

        boolean ifCorrectPassword = (passwordEncoder.matches(inputPassword, dbPassword));
        if (!ifCorrectPassword) throw new BadCredentialsException("You provided an incorrect password");

        return new UsernamePasswordAuthenticationToken(email, inputPassword);
    }
}
