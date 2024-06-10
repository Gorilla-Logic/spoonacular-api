package com.spoonacular.api.controllers;

import com.spoonacular.api.dtos.RecipeDTO;
import com.spoonacular.api.services.RecipeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The RecipeController class is a part of the controller layer in the MVC architecture of this Spring Boot application.
 * It handles HTTP requests related to recipes and communicates with the RecipeService to perform operations.
 * It includes endpoints for creating a new recipe, retrieving recipes by their IDs, searching for recipes,
 * updating a recipe's information, rating a recipe, and retrieving a recipe's rating.
 * Each method in this class corresponds to a specific endpoint and HTTP method.
 * The class is annotated with @RestController, indicating that it's a controller where every method returns a domain object instead of a view.
 * It's also annotated with @RequestMapping("/api"), meaning all its endpoints will be prefixed with "/api".
 */
@RestController
@RequestMapping("/api")
public class RecipeController {

    private final static Logger LOGGER = LoggerFactory.getLogger(RecipeController.class);
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("recipes/information")
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeDTO postRecipe(@RequestParam(name = "apiKey") String ApiKey,
                                @RequestBody RecipeDTO RecipeDTO) {
        return recipeService.save(RecipeDTO);
    }

    @GetMapping("recipes/{ids}")
    public List<RecipeDTO> getRecipes(@RequestParam(name = "apiKey") String ApiKey,
                                      @PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return recipeService.findAll(listIds);
    }

    @GetMapping("recipes/search")
    public List<RecipeDTO> searchRecipe(@RequestParam(name = "apiKey") String ApiKey,
                                        @RequestParam String query) {
        return recipeService.search(query);
    }

    @PutMapping("recipes/{id}/information")
    public ResponseEntity<RecipeDTO> putRecipe(@RequestParam(name = "apiKey") String ApiKey,
                                               @PathVariable String id,
                                               @RequestBody RecipeDTO RecipeDTO) {
        RecipeDTO recipeDTO = recipeService.findOne(id);
        if (recipeDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(recipeService.update(id, RecipeDTO));
    }

    @PatchMapping("recipes/{id}/rate/{rating}")
    public ResponseEntity<String> rateRecipe(@RequestParam(name = "apiKey") String ApiKey,
                                             @PathVariable String id,
                                             @PathVariable int rating) {
        RecipeDTO recipeDTO = recipeService.rate(id, rating);
        if (recipeDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok("OK");
    }

    @GetMapping("recipes/{id}/rating")
    public ResponseEntity<Double> getRatingRecipe(@RequestParam(name = "apiKey") String ApiKey,
                                                  @PathVariable String id) {
        Double rating = recipeService.getRating(id);
        if (rating == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(rating);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
