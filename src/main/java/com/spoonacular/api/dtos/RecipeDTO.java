package com.spoonacular.api.dtos;

import com.spoonacular.api.models.RecipeEntity;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * The RecipeDTO record is a data transfer object in this Spring Boot application.
 * The record includes the following fields:
 * - id: A string representing the id of the recipe.
 * - readyInMinutes: An integer representing the time required to prepare the recipe.
 * - sourceUrl: A string representing the URL of the source of the recipe.
 * - image: A string representing the URL of the image of the recipe.
 * - servings: An integer representing the number of servings the recipe makes.
 * - title: A string representing the title of the recipe.
 * - externalId: An integer representing the external id of the recipe.
 * The record includes a constructor that takes a RecipeEntity object as a parameter and initializes the fields of the record.
 * The record includes several methods to convert the RecipeDTO to a RecipeEntity:
 * - toRecipeEntity(): This method creates a new RecipeEntity object from the RecipeDTO.
 * - toRecipeEntityUpdate(String id): This method creates a new RecipeEntity object from the RecipeDTO with a specified id.
 * - toRecipeEntityRate(String id, int rating, RecipeEntity RecipeEntity): This method creates a new RecipeEntity object from the RecipeDTO with a specified id and rating.
 */
public record RecipeDTO(
        String id,
        int readyInMinutes,
        String sourceUrl,
        String image,
        int servings,
        String title,
        int externalId) {

    public RecipeDTO(RecipeEntity p) {
        this(p.getId() == null ? new ObjectId().toHexString() : p.getId().toHexString(),
                p.getReadyInMinutes(),
                p.getSourceUrl(),
                p.getImage(),
                p.getServings(),
                p.getTitle(),
                p.getExternalId());
    }

    public RecipeEntity toRecipeEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new RecipeEntity(_id, readyInMinutes, sourceUrl, image, servings, title, externalId);
    }

    public RecipeEntity toRecipeEntityUpdate(String id) {
        ObjectId _id = new ObjectId(id);
        return new RecipeEntity(_id, readyInMinutes, sourceUrl, image, servings, title, externalId);
    }

    public RecipeEntity toRecipeEntityRate(String id, int rating, RecipeEntity RecipeEntity) {
        ObjectId _id = new ObjectId(id);
        RecipeEntity recipeEntity = new RecipeEntity(_id, readyInMinutes, sourceUrl, image, servings, title, externalId);

        List<Integer> ratings = RecipeEntity.getRatings();
        if (ratings == null) ratings = new ArrayList<Integer>();
        ratings.add(rating);

        recipeEntity.setRatings(ratings);
        return recipeEntity;
    }
}
