package org.example.factory_core_manager.service;

import jakarta.transaction.Transactional;
import org.example.factory_core_manager.dto.SaveProductRecipe;
import org.example.factory_core_manager.entity.Inventory;
import org.example.factory_core_manager.entity.Product;
import org.example.factory_core_manager.entity.ProductRecipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeManufactoryService {

    private ProductService productService;
    private InventoryService inventoryService;
    private ProductRecipeService productRecipeService;

    @Autowired
    public RecipeManufactoryService(ProductService productService , InventoryService inventoryService , ProductRecipeService productRecipeService) {
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.productRecipeService = productRecipeService;
    }

    public void addNewRecipeForProduct(SaveProductRecipe saveProductRecipe){
        Product product = this.productService.getProductByName(saveProductRecipe.getProductName());
        Inventory inventory = this.inventoryService.getInventoryByName(saveProductRecipe.getInventoryName());
        ProductRecipe productRecipe = new ProductRecipe();
        productRecipe.setProduct(product);
        productRecipe.setInventory(inventory);
        productRecipe.setAmount(saveProductRecipe.getAmount());
        this.productRecipeService.save(productRecipe);
    }



}
