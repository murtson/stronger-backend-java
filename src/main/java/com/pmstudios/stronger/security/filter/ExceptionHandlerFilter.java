package com.pmstudios.stronger.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmstudios.stronger.exception.EntityNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// we need an exceptionHandler for filters, since they supposedly are getting called before serverDispatchlet?
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (UsernameNotFoundException e) {
            sendErrorResponse(HttpServletResponse.SC_NOT_FOUND, "The email provided in the JWT does not exist in our records", "Not Found", response, request);
        } catch (ExpiredJwtException e) {
            sendErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, "The JWT has expired", "Unauthorized", response, request);
        } catch (SignatureException e) {
            sendErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, "The JWT signature is invalid", "Unauthorized", response, request);
        } catch (JwtException e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            sendErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, "JWT EXCEPTION", "Unauthorized", response, request);
        } catch (RuntimeException e) {
            sendErrorResponse(HttpServletResponse.SC_BAD_REQUEST, "BAD REQUEST", "Bad Request", response, request);
        }
    }

    private void sendErrorResponse(int statusCode, String message, String error, HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(statusCode);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", statusCode);
        body.put("message", message);
        body.put("error", error);
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);

//        response.getWriter().write(message);
//        response.getWriter().flush();
    }

}
