package com.spoonacular.api.repositories;

import com.spoonacular.api.models.RecipeEntity;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The RecipeRepository interface is a repository interface in this Spring Boot application.
 * The interface is annotated with @Repository, indicating that it's a repository component in the Spring framework. It's automatically detected during classpath scanning.
 * The interface defines several methods for interacting with the database:
 * - save(RecipeEntity RecipeEntity): This method is intended to save a new RecipeEntity to the database and return it.
 * - findAll(List<String> ids): This method is intended to find and return all RecipeEntity objects with the given ids from the database.
 * - findOne(String id): This method is intended to find and return a RecipeEntity with the given id from the database.
 * - findOneByExternalId(int externalId): This method is intended to find and return a RecipeEntity with the given external id from the database.
 * - search(String query): This method is intended to find and return all RecipeEntity objects that match the given query from the database.
 * - update(RecipeEntity RecipeEntity): This method is intended to update a RecipeEntity in the database and return it.
 * - getRating(String id): This method is intended to calculate and return the average rating of a RecipeEntity with the given id from the database.
 */
@Repository
public interface RecipeRepository {

    RecipeEntity save(RecipeEntity RecipeEntity);

    List<RecipeEntity> findAll(List<String> ids);

    RecipeEntity findOne(String id);

    RecipeEntity findOneByExternalId(int externalId);

    List<RecipeEntity> search(String query);

    RecipeEntity update(RecipeEntity RecipeEntity);

    Double getRating(String id);
}
