package com.spoonacular.api.repositories;

import com.spoonacular.api.models.RecipeEntity;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The RecipeRepository interface is a part of the repository layer in the MVC architecture of this Spring Boot application.
 * It is used to interact with the database for operations related to RecipeEntity.
 * It is annotated with @Repository, indicating that it's a Data Access Object (DAO) providing the mechanism for storage, retrieval,
 * search, update and delete operation on objects.
 * The interface includes methods for saving a RecipeEntity, finding all RecipeEntity by their ids, finding a RecipeEntity by its id,
 * searching for RecipeEntity by a query string, updating a RecipeEntity, and getting the rating of a RecipeEntity by its id.
 * These methods are used to perform CRUD operations on the RecipeEntity table in the database.
 */
@Repository
public interface RecipeRepository {

    RecipeEntity save(RecipeEntity RecipeEntity);

    List<RecipeEntity> findAll(List<String> ids);

    RecipeEntity findOne(String id);

    List<RecipeEntity> search(String query);

    RecipeEntity update(RecipeEntity RecipeEntity);

    Double getRating(String id);
}
