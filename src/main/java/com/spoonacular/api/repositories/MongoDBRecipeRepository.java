package com.spoonacular.api.repositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.Indexes;
import com.spoonacular.api.models.RecipeEntity;

import jakarta.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Collection;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.ReturnDocument.AFTER;

/**
 * The MongoDBRecipeRepository class is a repository class in this Spring Boot application that implements the RecipeRepository interface.
 * The class is annotated with @Repository, indicating that it's a repository component in the Spring framework. It's automatically detected during classpath scanning.
 * The class includes a private MongoClient field for interacting with MongoDB, and a MongoCollection field for handling the collection of RecipeEntity objects.
 * The class includes a constructor that takes a MongoClient object as a parameter. This constructor is used for dependency injection.
 * The class includes an init method annotated with @PostConstruct, which is used to initialize the recipeCollection field after dependency injection is done.
 * The class implements several methods from the RecipeRepository interface:
 * - save(RecipeEntity RecipeEntity): This method saves a new RecipeEntity to the MongoDB collection and returns it.
 * - findAll(List<String> ids): This method finds and returns all RecipeEntity objects with the given ids from the MongoDB collection.
 * - findOne(String id): This method finds and returns a RecipeEntity with the given id from the MongoDB collection.
 * - findOneByExternalId(int externalId): This method finds and returns a RecipeEntity with the given external id from the MongoDB collection.
 * - search(String query): This method finds and returns all RecipeEntity objects that match the given query from the MongoDB collection.
 * - update(RecipeEntity RecipeEntity): This method updates a RecipeEntity in the MongoDB collection and returns it.
 * - getRating(String id): This method calculates and returns the average rating of a RecipeEntity with the given id from the MongoDB collection.
 */
@Repository
public class MongoDBRecipeRepository implements RecipeRepository {
    private final MongoClient client;
    private MongoCollection<RecipeEntity> recipeCollection;

    public MongoDBRecipeRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        recipeCollection = client.getDatabase("spoonacular").getCollection("recipes", RecipeEntity.class);
        recipeCollection.createIndex(Indexes.text("title"));
    }

    @Override
    public RecipeEntity save(RecipeEntity RecipeEntity) {
        RecipeEntity.setId(new ObjectId());
        recipeCollection.insertOne(RecipeEntity);
        return RecipeEntity;
    }

    @Override
    public List<RecipeEntity> findAll(List<String> ids) {
        return recipeCollection.find(in("_id", mapToObjectIds(ids))).into(new ArrayList<>());
    }

    @Override
    public RecipeEntity findOne(String id) {
        return recipeCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public RecipeEntity findOneByExternalId(int externalId) {
        return recipeCollection.find(eq("externalId", externalId)).first();
    }

    @Override
    public List<RecipeEntity> search(String query) {
        return recipeCollection.find(text(query)).into(new ArrayList<>());
    }

    @Override
    public RecipeEntity update(RecipeEntity RecipeEntity) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return recipeCollection.findOneAndReplace(eq("_id", RecipeEntity.getId()), RecipeEntity, options);
    }

    @Override
    public Double getRating(String id) {
        RecipeEntity recipeEntity = recipeCollection.find(eq("_id", new ObjectId(id))).first();
        if (recipeEntity == null) return null;

        return Optional.ofNullable(recipeEntity.getRatings())
                .stream()
                .flatMap(Collection::stream)
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0);
    }

    private List<ObjectId> mapToObjectIds(List<String> ids) {
        return ids
                .stream()
                .map(String::trim)
                .map(ObjectId::new)
                .toList();
    }
}
