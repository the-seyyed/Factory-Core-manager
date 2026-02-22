package org.example.factory_core_manager.repository;


import org.example.factory_core_manager.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Modifying
    @Transactional
    @Query("update Inventory inv  set inv.amount=:newAmount where inv.name =:inventoryName ")
    public void updateInventoryAmount(@Param("inventoryName") String productName, @Param("newAmount") long newAmount);

    @Query("select inv.amount from Inventory inv where inv.name =:inventoryName")
    public long findInventoryAmount(@Param("inventoryName") String productName);

    @Query("select inv.name from Inventory inv where inv.name=:checkName ")
    public String findIfExistInventoryName(@Param("checkName") String checkName);


    List<Inventory> findInventoryByName(String name);

    Inventory getInventoryByName(String name);
}
