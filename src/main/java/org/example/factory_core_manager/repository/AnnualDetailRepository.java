package org.example.factory_core_manager.repository;

import org.example.factory_core_manager.entity.AnnualDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnualDetailRepository extends JpaRepository<AnnualDetail, Long> {
}
