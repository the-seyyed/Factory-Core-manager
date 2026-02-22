package org.example.factory_core_manager.service;


import org.example.factory_core_manager.convertor.Convertor;
import org.example.factory_core_manager.dto.SaveProductRecipe;
import org.example.factory_core_manager.dto.SaveProductDto;
import org.example.factory_core_manager.entity.Product;
import org.example.factory_core_manager.exception.DuplicateProductNameException;
import org.example.factory_core_manager.exception.ProductNotExistException;
import org.example.factory_core_manager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {



    private ProductRepository productRepository;

    private Convertor convertor;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    public void saveNewProduct(SaveProductDto saveProductDto) {

        if(checkIfProductNameExists(saveProductDto.getName())) {
            throw new DuplicateProductNameException("Product name already exists : " + saveProductDto.getName());
        }

        Product productToSave = convertor.SaveProductDtoToProduct(saveProductDto);

        productRepository.save(productToSave);

    }

    private boolean checkIfProductNameExists(String productName) {

        return productRepository.findIfExistProductName(productName)!=null;

    }

    public void increaseStock(String productName , long amountToIncrease) {

        productRepository.updateAmount(productName,productRepository.findProductAmount(productName)+ amountToIncrease);

    }
    public ArrayList<String> getAllProductNames() {
        ArrayList<String> productNames = new ArrayList<>();
        productRepository.findAll().forEach(product -> productNames.add(product.getName()));
        return productNames;
    }



    public Product getProductByName(String productName) {
        if(!checkIfProductNameExists(productName)) {
            throw new ProductNotExistException("Product name does not exist : " + productName);
        }
        return this.productRepository.getProductByName(productName);
    }




}
