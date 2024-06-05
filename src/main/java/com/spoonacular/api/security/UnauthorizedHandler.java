package com.spoonacular.api.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * The UnauthorizedHandler class implements the AuthenticationEntryPoint interface provided by Spring Security.
 * It represents an entry point for the authentication process.
 * The class is annotated with @Component, indicating that it's a Spring managed bean and it can be auto-wired as needed.
 * The class includes a method named commence which takes HttpServletRequest, HttpServletResponse, and AuthenticationException as parameters.
 * This method is used to commence the authentication process.
 * If the authentication process fails, the method sends an HTTP 401 Unauthorized error along with the exception message.
 */
@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }

}
