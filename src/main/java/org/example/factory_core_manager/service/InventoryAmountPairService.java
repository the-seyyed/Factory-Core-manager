package org.example.factory_core_manager.service;

import org.example.factory_core_manager.dto.SaveInventoryAmountPair;
import org.example.factory_core_manager.entity.Inventory;
import org.example.factory_core_manager.entity.InventoryAmountPair;
import org.example.factory_core_manager.entity.Product;
import org.example.factory_core_manager.repository.InventoryAmountPairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryAmountPairService {

    private InventoryAmountPairRepository inventoryAmountPairRepository;
    private InventoryService inventoryService;

    @Autowired
    public InventoryAmountPairService(InventoryAmountPairRepository inventoryAmountPairRepository , InventoryService inventoryService) {
        this.inventoryAmountPairRepository = inventoryAmountPairRepository;
        this.inventoryService = inventoryService;
    }


    public InventoryAmountPair save(InventoryAmountPair inventoryAmountPair) {
        return inventoryAmountPairRepository.save(inventoryAmountPair);
    }

    public void save(Product product, SaveInventoryAmountPair saveInventoryAmountPair) {
        this.inventoryService.decreaseStock(saveInventoryAmountPair.getInventoryName() , saveInventoryAmountPair.getAmount());
        InventoryAmountPair inventoryAmountPair = new InventoryAmountPair();
        inventoryAmountPair.setProduct(product);
//        inventoryAmountPair.setInventory();
        inventoryAmountPair.setAmount(saveInventoryAmountPair.getAmount());
        inventoryAmountPairRepository.save(inventoryAmountPair);

    }



}
