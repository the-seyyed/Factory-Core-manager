package org.example.factory_core_manager.controller;


import org.example.factory_core_manager.dto.SaveInventoryAmountPair;
import org.example.factory_core_manager.dto.SaveProductDto;
import org.example.factory_core_manager.entity.Product;
import org.example.factory_core_manager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ProductManaging")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/saveNewProduct")
    public void saveNewProduct(@RequestBody SaveProductDto saveProductDto) {
        productService.saveNewProduct(saveProductDto);
    }

    @PostMapping("/updateAmount/{productName}/{amountToIncrease}")
    public void updateAmount(@PathVariable String productName,
                             @PathVariable long amountToIncrease) {

        productService.increaseStock(productName, amountToIncrease);

    }

    @PostMapping("/addNewRecipe/{productName}")
    public String addNewRecipe(@PathVariable String productName , @RequestBody SaveInventoryAmountPair inventoryAmountPair) {
        this.productService.addToRecipe(productName, inventoryAmountPair);
        return "recipe added successfully";
    }





}
