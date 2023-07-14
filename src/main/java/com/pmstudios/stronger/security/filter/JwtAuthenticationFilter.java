package com.pmstudios.stronger.security.filter;

import com.pmstudios.stronger.token.JwtService;
import com.pmstudios.stronger.security.SecurityConstants;
import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.user.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserServiceImpl userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {


        String authHeader = request.getHeader("Authorization"); // Bearer RefreshToken

        boolean noTokenInHeader = authHeader == null || !authHeader.startsWith(SecurityConstants.BEARER);
        if (noTokenInHeader) { // no token in header, send to next filter which is AuthenticationFilter
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authHeader.replace(SecurityConstants.BEARER, "");
        String userEmail = jwtService.getUsernameFromAccessToken(accessToken);

        boolean userNotAuthenticated = userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null;
        if (userNotAuthenticated) {
            User user = userService.loadUserByUsername(userEmail);
            if (jwtService.isAccessTokenValid(accessToken, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                System.out.println("RefreshToken for: " + userEmail + " was not valid");
            }
        }

        filterChain.doFilter(request, response);

//        String user = JWT.require(SecurityConstants.hashAlgorithm)
//                .build()
//                .verify(token)
//                .getSubject();
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, List.of());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        filterChain.doFilter(request, response);
    }
}
