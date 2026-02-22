package org.example.factory_core_manager.service;

import lombok.Getter;
import lombok.Setter;
import org.example.factory_core_manager.convertor.Convertor;
import org.example.factory_core_manager.dto.GetInventory;
import org.example.factory_core_manager.dto.SaveInventoryDto;
import org.example.factory_core_manager.entity.Inventory;
import org.example.factory_core_manager.exception.IllegalAmountToDecreaseInventoryException;
import org.example.factory_core_manager.exception.InventoryNotPersistedException;
import org.example.factory_core_manager.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Getter
@Setter
public class InventoryService {

    private Convertor convertor;

    private InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository , Convertor convertor) {
        this.inventoryRepository = inventoryRepository;
        this.convertor = convertor;
    }

    public void saveOrUpdateInventory(SaveInventoryDto saveInventoryDto){

        Inventory inventory = convertor.saveInventoryDtoToInventory(saveInventoryDto);

        if(checkIfInventoryNameExists(inventory.getName())) {
            increaseStock(inventory.getName() , inventory.getAmount());
        }

        inventoryRepository.save(inventory);

    }

    public void decreaseStock(String inventoryName , long amountToDecrease) {

        boolean isNotIllegalToReduce = inventoryRepository.findInventoryByName(inventoryName).getFirst().getAmount() >= amountToDecrease;
        if(!isNotIllegalToReduce)
            throw new IllegalAmountToDecreaseInventoryException("not being able to reduce the inventory : " + inventoryName);

        inventoryRepository.updateInventoryAmount(inventoryName,inventoryRepository.findInventoryAmount(inventoryName)-amountToDecrease);
    }

    public void editInventory(SaveInventoryDto saveInventoryDto , String inventoryName ){
        if(!this.checkIfInventoryNameExists(inventoryName))
            throw new InventoryNotPersistedException(inventoryName+ " not exist in inventory !\nyou must enter it first");
        Inventory inventory = inventoryRepository.findInventoryByName(inventoryName).getFirst();
        this.updateInventoryValues(inventory, saveInventoryDto);
        inventoryRepository.save(inventory);

    }
    public ArrayList<GetInventory> getAllInventories(){
        ArrayList<GetInventory> inventoryList = new ArrayList<>();
        this.inventoryRepository.findAll().forEach(inventory -> {
            inventoryList.add(this.convertor.inventoryToGetAllInventories(inventory));
        });
        return inventoryList;
    }

    public ArrayList<String> getAllInventoriesNames(){
        ArrayList<String> inventoriesName = new ArrayList<>();
        this.inventoryRepository.findAll().forEach(inventory -> inventoriesName.add(inventory.getName()));
        return inventoriesName;
    }

    public Inventory getInventoryByName(String inventoryName){
        if(!this.checkIfInventoryNameExists(inventoryName)){
            throw new InventoryNotPersistedException("Inventory does not exist : " + inventoryName);
        }
        return this.inventoryRepository.getInventoryByName(inventoryName);
    }




    //utility methods
    private void increaseStock(String inventoryName , long amountToIncrease) {
        inventoryRepository.updateInventoryAmount(inventoryName,inventoryRepository.findInventoryAmount(inventoryName)+ amountToIncrease);
    }

    public boolean checkIfInventoryNameExists(String inventoryName) {

        return inventoryRepository.findIfExistInventoryName(inventoryName)!=null;

    }

    private void updateInventoryValues(Inventory inventory , SaveInventoryDto saveInventoryDto) {
        if(saveInventoryDto.getName()!=null) {
            inventory.setName(saveInventoryDto.getName());
        }
        if (saveInventoryDto.getAmount()!=0){
            inventory.setAmount(saveInventoryDto.getAmount());
        }
    }






}
