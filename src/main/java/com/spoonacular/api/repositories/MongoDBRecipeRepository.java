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
 * The MongoDBRecipeRepository class implements the RecipeRepository interface.
 * It provides the concrete implementation of the methods declared in the RecipeRepository interface.
 * This class is responsible for performing CRUD operations on the RecipeEntity objects in the MongoDB database.
 * The class is annotated with @Repository, indicating that it's a Data Access Object (DAO) providing the mechanism for storage, retrieval,
 * search, update and delete operation on objects.
 * The class includes a constructor for creating a new MongoDBRecipeRepository object with a MongoClient parameter.
 * It also includes a @PostConstruct annotated method for initializing the MongoCollection with the appropriate database and collection.
 * The class overrides the save, findAll, findOne, search, update, and getRating methods from the RecipeRepository interface.
 * The save method is used to save a new RecipeEntity object in the database.
 * The findAll method is used to find all RecipeEntity objects in the database by their ids.
 * The findOne method is used to find a RecipeEntity object in the database by its id.
 * The search method is used to find RecipeEntity objects in the database by a query string.
 * The update method is used to update an existing RecipeEntity object in the database and return the updated object.
 * The getRating method is used to calculate the average rating of a RecipeEntity object.
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
