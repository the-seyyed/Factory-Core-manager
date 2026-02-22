package org.example.factory_core_manager.controller;


import org.example.factory_core_manager.dto.SaveProductRecipe;
import org.example.factory_core_manager.entity.ProductRecipe;
import org.example.factory_core_manager.service.ProductRecipeService;
import org.example.factory_core_manager.service.RecipeManufactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipe-product")
public class ProductRecipeController {

    private RecipeManufactoryService recipeManufactoryService;

    @Autowired
    public ProductRecipeController(RecipeManufactoryService recipeManufactoryService) {
        this.recipeManufactoryService = recipeManufactoryService;
    }

    @PostMapping("/add-product-recipe")
    public void addProductRecipe(@RequestBody SaveProductRecipe saveProductRecipe) {
        this.recipeManufactoryService.addNewRecipeForProduct(saveProductRecipe);
    }


}
