package com.pmstudios.stronger.security.manager;

import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Supplier;

//@Component
//@RequiredArgsConstructor
//public class UserAuthorizationManager implements AuthorizationManager<FilterInvocation> {
//
//    private final UserService userService;
//    private final RequestMatcher requestMatcher = new AntPathRequestMatcher("/user/{userId}/**");
//
//    @Override
//    public AuthorizationDecision check(Supplier<Authentication> authentication, FilterInvocation object) {
//        HttpServletRequest request = object.getRequest();
//
//        if (requestMatcher.matches(request)) {
//
//            User authUser = (User) authentication.get().getPrincipal();
//            String requstUserId =
//
//            String userId = extractUserIdFromResource(request);
//            UserDetails userDetails = (UserDetails) authentication.get().getPrincipal();
//            if (userId != null && userDetails.getUsername().equals(userId)) {
//                return new AuthorizationDecision(ACCESS_GRANTED); // Grant access
//            }
//
//            return new AuthorizationDecision(ACCESS_DENIED); // Deny access
//        }
//
//        return new AuthorizationDecision(ACCESS_ABSTAIN); // Abstain from decision
//    }
//
//    private String extractUserIdFromResource(HttpServletRequest request) {
//        // Implement the logic to extract the user ID from the request object
//        // For example, you can use request.getPathInfo() or custom extraction methods
//        return null;
//    }
//}
