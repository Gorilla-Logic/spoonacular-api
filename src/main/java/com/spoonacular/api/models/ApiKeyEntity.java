package com.spoonacular.api.models;

import org.bson.types.ObjectId;

import java.util.Objects;

/**
 * The ApiKeyEntity class represents an API Key entity in the application.
 * It includes properties for id, apiKey, hits, and hitsLimit.
 * The class includes constructors for creating a new ApiKeyEntity object with or without parameters.
 * It also includes getter and setter methods for each property.
 * The class overrides the equals, hashCode, and toString methods from the Object class.
 * The equals method is used to check if two ApiKeyEntity objects are equal based on their properties.
 * The hashCode method returns a hash code value for the object, which is used in hash-based collections such as HashMap and HashSet.
 * The toString method returns a string representation of the object, which can be useful for debugging.
 */
public class ApiKeyEntity {

    private ObjectId id;
    private String apiKey;
    private int hits;
    private int hitsLimit;

    public ApiKeyEntity() {
    }

    public ApiKeyEntity(ObjectId id, String apiKey, int hits, int hitsLimit) {
        this.id = id;
        this.apiKey = apiKey;
        this.hits = hits;
        this.hitsLimit = hitsLimit;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getHitsLimit() {
        return hitsLimit;
    }

    public void setHitsLimit(int hitsLimit) {
        this.hitsLimit = hitsLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiKeyEntity that = (ApiKeyEntity) o;
        return hits == that.hits && hitsLimit == that.hitsLimit && Objects.equals(id, that.id) && Objects.equals(apiKey, that.apiKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apiKey, hits, hitsLimit);
    }

    @Override
    public String toString() {
        return "ApiKeyEntity{" +
                "id=" + id +
                ", apiKey='" + apiKey + '\'' +
                ", hits=" + hits +
                ", hitsLimit=" + hitsLimit +
                '}';
    }
}
