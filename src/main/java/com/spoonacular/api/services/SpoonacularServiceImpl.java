package com.spoonacular.api.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * The SpoonacularServiceImpl class is a service class in this Spring Boot application that implements the SpoonacularService interface.
 * The class is annotated with @Service, indicating that it's a service component in the Spring framework. It's automatically detected during classpath scanning.
 * The class includes a private field named restTemplate which is a Spring's central class for synchronous HTTP client-side access.
 * It also includes two private fields, apiKey and baseUrl, which are injected from the application's properties file.
 * The class includes a constructor that takes a RestTemplateBuilder object as a parameter. This constructor is used for dependency injection and to build the restTemplate.
 * The class implements two methods from the SpoonacularService interface:
 * - findOne(int id): This method constructs the URI for the Spoonacular API, makes a GET request to the API, and returns a JsonObject that represents a recipe with the given id.
 * - search(String query): This method constructs the URI for the Spoonacular API, makes a GET request to the API, and returns a JsonObject that represents a recipe matching the given query string.
 */
@Service
public class SpoonacularServiceImpl implements SpoonacularService {

    private final RestTemplate restTemplate;
    @Value("${application.security.spoonacular.apiKey}")
    private String apiKey;
    @Value("${application.security.spoonacular.baseUrl}")
    private String baseUrl;

    public SpoonacularServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public JsonObject findOne(int id) {
        String uri = String.format("%s/%s/information?apiKey=%s", baseUrl, id, apiKey);
        String response = this.restTemplate.getForObject(uri, String.class);

        if (response != null && !response.isEmpty()) return JsonParser.parseString(response).getAsJsonObject();
        return new JsonObject();
    }

    @Override
    public JsonObject search(String query) {
        String uri = String.format("%s/search?query=%s&apiKey=%s", baseUrl, query, apiKey);
        String response = this.restTemplate.getForObject(uri, String.class);

        return JsonParser.parseString(response).getAsJsonObject();
    }
}
