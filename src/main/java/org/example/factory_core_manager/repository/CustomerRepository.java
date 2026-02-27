package org.example.factory_core_manager.repository;

import org.example.factory_core_manager.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsCustomerByCustomerCode(String customerCode);

    boolean existsCustomerByLastName(String lastName);
}
