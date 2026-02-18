package org.example.factory_core_manager.repository;

import org.example.factory_core_manager.entity.PaymentDegree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface PaymentDegreeRepository extends JpaRepository<PaymentDegree, Long> {
}
