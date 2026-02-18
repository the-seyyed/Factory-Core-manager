package org.example.factory_core_manager.repository;


import org.example.factory_core_manager.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query("select p.name from Product p where p.name=:checkName ")
    public String findIfExistProductName(@Param("checkName") String checkName);


    @Modifying
    @Transactional
    @Query("update Product p  set p.amount=:newAmount where p.name =:productName ")
    public void updateAmount(@Param("productName") String productName, @Param("newAmount") long newAmount);

    @Query("select p.amount from Product p where p.name =:productName")
    public long findProductAmount(@Param("productName") String productName);

    Product getProductByName(String name);
}
