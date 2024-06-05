package com.spoonacular.api.services;

import com.spoonacular.api.dtos.RecipeDTO;

import java.util.List;

/**
 * The RecipeService interface defines the contract for the Recipe service layer.
 * It includes methods for saving a RecipeDTO, finding all RecipeDTOs by a list of ids, finding one RecipeDTO by id,
 * searching for RecipeDTOs by a query, updating a RecipeDTO by id, rating a RecipeDTO by id, and getting the rating of a RecipeDTO by id.
 * Each method is expected to be implemented by a class that provides the actual logic for interacting with the data source.
 */
public interface RecipeService {

    RecipeDTO save(RecipeDTO RecipeDTO);

    List<RecipeDTO> findAll(List<String> ids);

    RecipeDTO findOne(String id);

    List<RecipeDTO> search(String query);

    RecipeDTO update(String id, RecipeDTO RecipeDTO);

    RecipeDTO rate(String id, int rating);

    Double getRating(String id);
}
