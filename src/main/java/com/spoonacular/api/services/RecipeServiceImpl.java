package com.spoonacular.api.services;

import com.spoonacular.api.dtos.RecipeDTO;
import com.spoonacular.api.models.RecipeEntity;
import com.spoonacular.api.repositories.RecipeRepository;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The RecipeServiceImpl class implements the RecipeService interface.
 * It provides the actual logic for interacting with the data source for RecipeDTOs.
 * The class is annotated with @Service, indicating that it's a Spring managed bean and it can be auto-wired as needed.
 * The class includes a constructor for creating a new RecipeServiceImpl object with a RecipeRepository parameter.
 * This repository is used to interact with the database for operations related to RecipeEntity.
 * The class includes methods for saving a RecipeDTO, finding all RecipeDTOs by a list of ids, finding one RecipeDTO by id,
 * searching for RecipeDTOs by a query, updating a RecipeDTO by id, rating a RecipeDTO by id, and getting the rating of a RecipeDTO by id.
 * Each method interacts with the RecipeRepository to perform the corresponding database operation and returns the result as a RecipeDTO or a list of RecipeDTOs.
 */
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository RecipeRepository) {
        this.recipeRepository = RecipeRepository;
    }

    @Override
    public RecipeDTO save(RecipeDTO RecipeDTO) {
        return new RecipeDTO(recipeRepository.save(RecipeDTO.toRecipeEntity()));
    }

    @Override
    public List<RecipeDTO> findAll(List<String> ids) {
        return recipeRepository.findAll(ids).stream().map(RecipeDTO::new).toList();
    }

    @Override
    public RecipeDTO findOne(String id) {
        return new RecipeDTO(recipeRepository.findOne(id));
    }

    @Override
    public List<RecipeDTO> search(String query) {
        return recipeRepository.search(query).stream().map(RecipeDTO::new).toList();
    }

    @Override
    public RecipeDTO update(String id, RecipeDTO RecipeDTO) {
        return new RecipeDTO(recipeRepository.update(RecipeDTO.toRecipeEntityUpdate(id)));
    }

    @Override
    public RecipeDTO rate(String id, int rating) {
        RecipeEntity recipeEntity = recipeRepository.findOne(id);
        if (recipeEntity == null) return null;

        RecipeDTO RecipeDTO = new RecipeDTO(recipeEntity);
        return new RecipeDTO(recipeRepository.update(RecipeDTO.toRecipeEntityRate(id, rating, recipeEntity)));
    }

    @Override
    public Double getRating(String id) {
        return recipeRepository.getRating(id);
    }
}
