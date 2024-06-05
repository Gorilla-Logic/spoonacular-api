package com.spoonacular.api.security;

import com.spoonacular.api.models.ApiKeyEntity;
import com.spoonacular.api.repositories.ApiKeyRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * The ApiKeyAuthExtractor class is a part of the security layer in this Spring Boot application.
 * It is used to extract the API key from the incoming HTTP request and perform authentication.
 * The class is annotated with @Component, indicating that it's a Spring managed bean and it can be auto-wired as needed.
 * The class includes a constructor for creating a new ApiKeyAuthExtractor object with an ApiKeyRepository parameter.
 * This repository is used to interact with the database for operations related to ApiKeyEntity.
 * The class includes a method named extract which takes an HttpServletRequest as a parameter.
 * This method is used to extract the API key from the request, find the corresponding ApiKeyEntity in the database,
 * check the hits limit, update the hits count, and return an Optional Authentication object.
 * If the API key is not provided, the ApiKeyEntity is not found, or the hits limit is reached, the method returns an empty Optional.
 */
@Component
public class ApiKeyAuthExtractor {

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyAuthExtractor(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public Optional<Authentication> extract(HttpServletRequest request) {
        String providedKey = request.getParameter("apiKey");
        if (providedKey == null)
            return Optional.empty();

        ApiKeyEntity apiKeyEntity = this.apiKeyRepository.findOne(providedKey);
        if (apiKeyEntity == null)
            return Optional.empty();

        int hitsLimit = apiKeyEntity.getHitsLimit();
        if (apiKeyEntity.getHits() >= hitsLimit)
            return Optional.empty();

        apiKeyEntity.setHits(apiKeyEntity.getHits()+1);
        this.apiKeyRepository.update(apiKeyEntity);

        return Optional.of(new ApiKeyAuth(providedKey, AuthorityUtils.NO_AUTHORITIES));
    }

}
