package com.spoonacular.api.dtos;

import com.spoonacular.api.models.RecipeEntity;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * The RecipeDTO record is a Data Transfer Object (DTO) used for transferring data between subsystems of the application.
 * It is used to represent a recipe in a form that is convenient for the client-side of the application.
 * It includes fields for id, readyInMinutes, sourceUrl, image, servings, and title.
 * The record also includes methods for converting between RecipeDTO and RecipeEntity objects.
 * These methods are used when retrieving data from the database and sending it to the client, or vice versa.
 * The RecipeDTO(RecipeEntity p) constructor is used to create a RecipeDTO object from a RecipeEntity object.
 * The toRecipeEntity() method is used to convert a RecipeDTO object back into a RecipeEntity object.
 * The toRecipeEntityUpdate(String id) method is used to convert a RecipeDTO object into a RecipeEntity object with a specific id.
 * The toRecipeEntityRate(String id, int rating, RecipeEntity RecipeEntity) method is used to convert a RecipeDTO object into a RecipeEntity object with a specific id and rating.
 */
public record RecipeDTO(
        String id,
        int readyInMinutes,
        String sourceUrl,
        String image,
        int servings,
        String title) {

    public RecipeDTO(RecipeEntity p) {
        this(p.getId() == null ? new ObjectId().toHexString() : p.getId().toHexString(),
            p.getReadyInMinutes(),
            p.getSourceUrl(),
            p.getImage(),
            p.getServings(),
            p.getTitle());
    }

    public RecipeEntity toRecipeEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new RecipeEntity(_id, readyInMinutes, sourceUrl, image, servings, title);
    }

    public RecipeEntity toRecipeEntityUpdate(String id) {
        ObjectId _id = new ObjectId(id);
        return new RecipeEntity(_id, readyInMinutes, sourceUrl, image, servings, title);
    }

    public RecipeEntity toRecipeEntityRate(String id, int rating, RecipeEntity RecipeEntity) {
        ObjectId _id = new ObjectId(id);
        RecipeEntity recipeEntity = new RecipeEntity(_id, readyInMinutes, sourceUrl, image, servings, title);

        List<Integer> ratings = RecipeEntity.getRatings();
        if (ratings == null) ratings = new ArrayList<Integer>();
        ratings.add(rating);

        recipeEntity.setRatings(ratings);
        return recipeEntity;
    }
}
