package org.example.factory_core_manager.service;

import org.example.factory_core_manager.dto.SaveProductRecipe;
import org.example.factory_core_manager.entity.ProductRecipe;
import org.example.factory_core_manager.repository.ProductRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductRecipeService {

    private ProductRecipeRepository productRecipeRepository;

    @Autowired
    public ProductRecipeService(ProductRecipeRepository productRecipeRepository) {
        this.productRecipeRepository = productRecipeRepository;

    }


    public ProductRecipe save(ProductRecipe productRecipe) {
        return productRecipeRepository.save(productRecipe);
    }






}
