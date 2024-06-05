package com.spoonacular.api.repositories;

import com.spoonacular.api.models.ApiKeyEntity;

import org.springframework.stereotype.Repository;

/**
 * The ApiKeyRepository interface is a part of the repository layer in the MVC architecture of this Spring Boot application.
 * It is used to interact with the database for operations related to ApiKeyEntity.
 * It is annotated with @Repository, indicating that it's a Data Access Object (DAO) providing the mechanism for storage, retrieval,
 * search, update and delete operation on objects.
 * The interface includes methods for finding an ApiKeyEntity by its id and updating an ApiKeyEntity.
 * These methods are used to perform CRUD operations on the ApiKeyEntity table in the database.
 */
@Repository
public interface ApiKeyRepository {

    ApiKeyEntity findOne(String id);

    ApiKeyEntity update(ApiKeyEntity ApiKeyEntity);
}
