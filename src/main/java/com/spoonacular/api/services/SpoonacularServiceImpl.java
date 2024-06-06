package com.spoonacular.api.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.spoonacular.api.models.RecipeEntity;
import com.spoonacular.api.repositories.RecipeRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * The SpoonacularServiceImpl class is a service class in this Spring Boot application that implements the SpoonacularService interface.
 * The class is annotated with @Service, indicating that it's a service component in the Spring framework. It's automatically detected during classpath scanning.
 * The class includes two private fields, apiKey and baseUrl, which are injected from the application's properties file. It also includes a RestTemplate object for making HTTP requests and a RecipeRepository object for interacting with the database.
 * The class includes a constructor that takes a RestTemplateBuilder and a RecipeRepository object as parameters. This constructor is used for dependency injection and to build the restTemplate.
 * The class implements two methods from the SpoonacularService interface:
 * - findOne(int id): This method constructs the URI for the Spoonacular API, makes a GET request to the API, and returns a JsonObject that represents a recipe with the given id. It also updates the corresponding recipe in the database.
 * - search(String query): This method constructs the URI for the Spoonacular API, makes a GET request to the API, and returns a JsonObject that represents a recipe matching the given query string. It also saves the corresponding recipes in the database.
 */
@Service
public class SpoonacularServiceImpl implements SpoonacularService {

    @Value("${application.security.spoonacular.apiKey}")
    private String apiKey;
    @Value("${application.security.spoonacular.baseUrl}")
    private String baseUrl;
    private final RestTemplate restTemplate;
    private final RecipeRepository recipeRepository;

    public SpoonacularServiceImpl(RestTemplateBuilder restTemplateBuilder, RecipeRepository RecipeRepository) {
        this.restTemplate = restTemplateBuilder.build();
        this.recipeRepository = RecipeRepository;
    }

    @Override
    public JsonObject findOne(int id) {
        String uri = String.format("%s/%s/information?apiKey=%s", baseUrl, id, apiKey);
        String response = this.restTemplate.getForObject(uri, String.class);
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();

        RecipeEntity recipeEntity = recipeRepository.findOneByExternalId(id);

        if (Optional.ofNullable(jsonObject.get("title")).isPresent()) recipeEntity.setTitle(jsonObject.get("title").getAsString());
        if (Optional.ofNullable(jsonObject.get("image")).isPresent()) recipeEntity.setImage(jsonObject.get("image").getAsString());
        if (Optional.ofNullable(jsonObject.get("servings")).isPresent()) recipeEntity.setServings(jsonObject.get("servings").getAsInt());
        if (Optional.ofNullable(jsonObject.get("readyInMinutes")).isPresent()) recipeEntity.setReadyInMinutes(jsonObject.get("readyInMinutes").getAsInt());

        recipeRepository.update(recipeEntity);

        return jsonObject;
    }

    @Override
    public JsonObject search(String query) {
        String uri = String.format("%s/search?query=%s&apiKey=%s", baseUrl, query, apiKey);
        String response = this.restTemplate.getForObject(uri, String.class);
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();

        jsonObject.getAsJsonArray("results").forEach(result -> {
            JsonObject recipe = result.getAsJsonObject();
            RecipeEntity recipeEntity = new RecipeEntity();

            if (Optional.ofNullable(recipe.get("id")).isPresent()) recipeEntity.setExternalId(recipe.get("id").getAsInt());
            if (Optional.ofNullable(recipe.get("title")).isPresent()) recipeEntity.setTitle(recipe.get("title").getAsString());
            if (Optional.ofNullable(recipe.get("image")).isPresent()) recipeEntity.setImage(recipe.get("image").getAsString());
            if (Optional.ofNullable(recipe.get("servings")).isPresent()) recipeEntity.setServings(recipe.get("servings").getAsInt());
            if (Optional.ofNullable(recipe.get("readyInMinutes")).isPresent()) recipeEntity.setReadyInMinutes(recipe.get("readyInMinutes").getAsInt());

            recipeRepository.save(recipeEntity);
        });
        return jsonObject;
    }
}
