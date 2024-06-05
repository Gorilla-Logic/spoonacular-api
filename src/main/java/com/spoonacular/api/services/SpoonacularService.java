package com.spoonacular.api.services;

import com.google.gson.JsonObject;

/**
 * The SpoonacularService interface provides the structure for a service class in the application.
 * This interface defines two methods:
 * - findOne(int id): This method is intended to find and return a JsonObject that represents a recipe with the given id.
 * - search(String query): This method is intended to find and return a JsonObject that represents a recipe matching the given query string.
 * Implementations of this interface are expected to provide the specific logic for interacting with the Spoonacular API.
 */
public interface SpoonacularService {

    JsonObject findOne(int id);

    JsonObject search(String query);
}
