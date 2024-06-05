package com.spoonacular.api.repositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.Indexes;
import com.spoonacular.api.models.ApiKeyEntity;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.ReturnDocument.AFTER;

/**
 * The MongoDBApiKeyRepository class implements the ApiKeyRepository interface.
 * It provides the concrete implementation of the methods declared in the ApiKeyRepository interface.
 * This class is responsible for performing CRUD operations on the ApiKeyEntity objects in the MongoDB database.
 * The class is annotated with @Repository, indicating that it's a Data Access Object (DAO) providing the mechanism for storage, retrieval,
 * search, update and delete operation on objects.
 * The class includes a constructor for creating a new MongoDBApiKeyRepository object with a MongoClient parameter.
 * It also includes a @PostConstruct annotated method for initializing the MongoCollection with the appropriate database and collection.
 * The class overrides the findOne and update methods from the ApiKeyRepository interface.
 * The findOne method is used to find an ApiKeyEntity object in the database by its apiKey.
 * The update method is used to update an existing ApiKeyEntity object in the database and return the updated object.
 */
@Repository
public class MongoDBApiKeyRepository implements ApiKeyRepository {
    private final MongoClient client;
    private MongoCollection<ApiKeyEntity> apiKeyCollection;

    public MongoDBApiKeyRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        apiKeyCollection = client.getDatabase("spoonacular").getCollection("api_keys", ApiKeyEntity.class);
        apiKeyCollection.createIndex(Indexes.text("apiKey"));
    }

    @Override
    public ApiKeyEntity findOne(String apiKey) {
        return apiKeyCollection.find(eq("apiKey",apiKey)).first();
    }

    @Override
    public ApiKeyEntity update(ApiKeyEntity ApiKeyEntity) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return apiKeyCollection.findOneAndReplace(eq("_id", ApiKeyEntity.getId()), ApiKeyEntity, options);
    }
}
