package com.spoonacular.api.models;

import lombok.Getter;
import lombok.Setter;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.Objects;

/**
 * The RecipeEntity class is a model class in this Spring Boot application.
 * The class includes the following fields:
 * - id: An ObjectId representing the id of the recipe.
 * - readyInMinutes: An integer representing the time required to prepare the recipe.
 * - sourceUrl: A string representing the URL of the source of the recipe.
 * - image: A string representing the URL of the image of the recipe.
 * - servings: An integer representing the number of servings the recipe makes.
 * - title: A string representing the title of the recipe.
 * - ratings: A list of integers representing the ratings of the recipe.
 * - externalId: An integer representing the external id of the recipe.
 * The class includes a default constructor and a constructor that initializes all the fields of the class.
 * The class includes getter and setter methods for all the fields.
 * The class overrides the equals and hashCode methods from the Object class to compare RecipeEntity objects based on their fields.
 * The class also overrides the toString method from the Object class to provide a string representation of the RecipeEntity object.
 */
public class RecipeEntity {

    private ObjectId id;
    private int readyInMinutes;
    private String sourceUrl;
    private String image;
    private int servings;
    private String title;
    private List<Integer> ratings;
    @Getter
    @Setter
    private int externalId;

    public RecipeEntity() {
    }

    public RecipeEntity(ObjectId id,
                        int readyInMinutes,
                        String sourceUrl,
                        String image,
                        int servings,
                        String title,
                        int externalId) {
        this.id = id;
        this.readyInMinutes = readyInMinutes;
        this.sourceUrl = sourceUrl;
        this.image = image;
        this.servings = servings;
        this.title = title;
        this.externalId = externalId;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public void setRatings(List<Integer> ratings) {
        this.ratings = ratings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeEntity that = (RecipeEntity) o;
        return readyInMinutes == that.readyInMinutes && servings == that.servings && externalId == that.externalId && Objects.equals(id, that.id) && Objects.equals(sourceUrl, that.sourceUrl) && Objects.equals(image, that.image) && Objects.equals(title, that.title) && Objects.equals(ratings, that.ratings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, readyInMinutes, sourceUrl, image, servings, title, ratings, externalId);
    }

    @Override
    public String toString() {
        return "RecipeEntity{" +
                "id=" + id +
                ", readyInMinutes=" + readyInMinutes +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", image='" + image + '\'' +
                ", servings=" + servings +
                ", title='" + title + '\'' +
                ", ratings=" + ratings +
                ", externalId=" + externalId +
                '}';
    }
}
