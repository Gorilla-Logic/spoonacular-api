package com.spoonacular.api.models;

import lombok.Getter;
import lombok.Setter;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.Objects;

/**
 * The RecipeEntity class represents a Recipe entity in the application.
 * It includes properties for id, readyInMinutes, sourceUrl, image, servings, title, and ratings.
 * The class includes constructors for creating a new RecipeEntity object with or without parameters.
 * It also includes getter and setter methods for each property.
 * The class overrides the equals, hashCode, and toString methods from the Object class.
 * The equals method is used to check if two RecipeEntity objects are equal based on their properties.
 * The hashCode method returns a hash code value for the object, which is used in hash-based collections such as HashMap and HashSet.
 * The toString method returns a string representation of the object, which can be useful for debugging.
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
