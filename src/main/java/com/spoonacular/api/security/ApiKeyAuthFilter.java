package com.spoonacular.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * The ApiKeyAuthFilter class extends the OncePerRequestFilter class provided by Spring Security.
 * It represents a filter that is executed once per request.
 * The class is annotated with @Component, indicating that it's a Spring managed bean and it can be auto-wired as needed.
 * It is also annotated with @RequiredArgsConstructor, which is a Lombok annotation that generates a constructor with required fields.
 * In this case, the required field is an ApiKeyAuthExtractor object.
 * The class includes a method named doFilterInternal which takes HttpServletRequest, HttpServletResponse, and FilterChain as parameters.
 * This method is used to extract the API key from the request using the extractor, set the authentication in the SecurityContext if the API key is present,
 * and then continue with the filter chain.
 * If the API key is not present, the filter chain is continued without setting the authentication in the SecurityContext.
 */
@Component
@RequiredArgsConstructor
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private final ApiKeyAuthExtractor extractor;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        extractor.extract(request)
                .ifPresent(SecurityContextHolder.getContext()::setAuthentication);

        filterChain.doFilter(request, response);
    }
}
