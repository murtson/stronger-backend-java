package com.pmstudios.stronger.security;

import com.pmstudios.stronger.security.filter.ExceptionHandlerFilter;
import com.pmstudios.stronger.security.filter.JwtAuthenticationFilter;
import com.pmstudios.stronger.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration // Configuration: tell spring the class is a source of bean definitions'
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserServiceImpl userServiceImpl;
    private final CustomAuthenticationEntryPoint unauthorizedHandler;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(SecurityConstants.REGISTER_PATH, SecurityConstants.LOGIN_PATH).permitAll() // white list auth paths
                .anyRequest().authenticated(); // all requests should be authenticated, is this the authentication manager?

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // could add custom AuthenticationFilter
        http.addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class);

        // filters: exceptionHandlerFilter --> jwtAuthFilter --> UsernamePasswordAuthenticationFilter

        http.authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean // data access object, which is responsible for fetching user details and encode password
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userServiceImpl);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://127.0.0.1:5173")); // Replace with the origin of your frontend application
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // Specify the HTTP methods allowed
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Specify the headers allowed

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
