package com.spoonacular.api.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * The ApiKeyAuth class extends the AbstractAuthenticationToken class provided by Spring Security.
 * It represents an authentication token that is associated with an API key.
 * The class includes a constructor for creating a new ApiKeyAuth object with an API key and a collection of granted authorities.
 * The constructor sets the authenticated status to true.
 * The class overrides the getCredentials and getPrincipal methods from the AbstractAuthenticationToken class.
 * The getCredentials method returns null as the credentials are not needed for API key authentication.
 * The getPrincipal method returns the API key associated with this authentication token.
 */
public class ApiKeyAuth extends AbstractAuthenticationToken {

    private final String apiKey;

    public ApiKeyAuth(String apiKey, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.apiKey = apiKey;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return apiKey;
    }
}
