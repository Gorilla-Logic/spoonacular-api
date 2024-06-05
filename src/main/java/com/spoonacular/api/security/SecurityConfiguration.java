package com.spoonacular.api.security;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * The SecurityConfiguration class is a part of the security layer in this Spring Boot application.
 * It is used to configure the security settings for the application.
 * The class is annotated with @Configuration, indicating that it's a configuration class that may have @Bean annotated methods.
 * It is also annotated with @EnableWebSecurity, indicating that it's a configuration class for Spring Security.
 * The @RequiredArgsConstructor annotation is a Lombok annotation that generates a constructor with required fields.
 * In this case, the required fields are an ApiKeyAuthFilter object and an UnauthorizedHandler object.
 * The class includes a method named securityFilterChain which takes an HttpSecurity object as a parameter.
 * This method is used to configure the security settings for the application.
 * It disables CORS and CSRF, sets the session creation policy to STATELESS, disables form login, sets the authentication entry point,
 * sets the security matcher, authorizes HTTP requests, and adds the ApiKeyAuthFilter before the UsernamePasswordAuthenticationFilter in the filter chain.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final ApiKeyAuthFilter authFilter;
    private final UnauthorizedHandler unauthorizedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(AbstractHttpConfigurer::disable)
            .exceptionHandling(configurer -> configurer.authenticationEntryPoint(unauthorizedHandler))
            .securityMatcher("/api/**")
            .authorizeHttpRequests(
                registry -> registry
                .requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui/**"))
                .permitAll()
                .anyRequest()
                .authenticated()
            )
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

}
