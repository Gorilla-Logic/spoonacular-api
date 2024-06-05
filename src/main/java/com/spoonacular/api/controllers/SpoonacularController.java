package com.spoonacular.api.controllers;

import com.spoonacular.api.services.SpoonacularService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * The SpoonacularController class is a REST controller in this Spring Boot application.
 * The class is annotated with @RestController, indicating that it's a controller where every method returns a domain object instead of a view.
 * It's shorthand for @Controller and @ResponseBody rolled together.
 * The class is also annotated with @RequestMapping("/api"), indicating that the URL of the API this controller handles starts with "/api".
 * The class includes a private field named spoonacularService which is a service class for handling the business logic.
 * The class includes a constructor that takes a SpoonacularService object as a parameter. This constructor is used for dependency injection.
 * The class includes two GET endpoints:
 * - "/api/spoonacular/{id}" which produces JSON and uses the spoonacularService to find a recipe by its id.
 * - "/api/spoonacular/search" which also produces JSON and uses the spoonacularService to search for a recipe by a query string.
 * The class includes an exception handler for RuntimeException. If a RuntimeException is thrown, it returns a HTTP status of INTERNAL_SERVER_ERROR and logs the error.
 */
@RestController
@RequestMapping("/api")
public class SpoonacularController {

    private final static Logger LOGGER = LoggerFactory.getLogger(SpoonacularController.class);
    private final SpoonacularService spoonacularService;

    public SpoonacularController(SpoonacularService spoonacularService) {
        this.spoonacularService = spoonacularService;
    }

    @GetMapping(value = "spoonacular/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRecipe(@RequestParam(name = "apiKey") String ApiKey, @PathVariable int id) {
        return spoonacularService.findOne(id).toString();
    }

    @GetMapping(value = "spoonacular/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public String searchRecipe(@RequestParam(name = "apiKey") String ApiKey, @RequestParam String query){
        return spoonacularService.search(query).toString();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
