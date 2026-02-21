package org.example.factory_core_manager.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.factory_core_manager.entity.Inventory;

@Getter
@Setter
public class SaveInventoryAmountPair {

    private String inventoryName ;
    private Long amount ;
}
