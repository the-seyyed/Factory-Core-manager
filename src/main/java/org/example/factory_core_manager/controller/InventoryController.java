package org.example.factory_core_manager.controller;


import org.example.factory_core_manager.dto.GetInventory;
import org.example.factory_core_manager.dto.SaveInventoryDto;
import org.example.factory_core_manager.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/inventory_managing")
public class InventoryController {

    private InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/addNewInventory")
    public void addNewInventory(@RequestBody SaveInventoryDto saveInventoryDto) {
        this.inventoryService.saveOrUpdateInventory(saveInventoryDto);
//        return "inventory added or updated successfully";
    }

    @PostMapping("edit/{inventoryName}")
    public String editInventory(@RequestBody SaveInventoryDto saveInventoryDto , @PathVariable String inventoryName) {
        this.inventoryService.editInventory(saveInventoryDto, inventoryName);
        return "inventory edited successfully";
    }
    @GetMapping("/AllInventories")
    public ArrayList<GetInventory> showAllInventories() {
        return this.inventoryService.getAllInventories();
    }



}
