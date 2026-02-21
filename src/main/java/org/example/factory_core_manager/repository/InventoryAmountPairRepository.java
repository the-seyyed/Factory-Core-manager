package org.example.factory_core_manager.repository;


import org.example.factory_core_manager.entity.InventoryAmountPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryAmountPairRepository extends JpaRepository<InventoryAmountPair, Long> {
}
