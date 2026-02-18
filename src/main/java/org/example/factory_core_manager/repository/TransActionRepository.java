package org.example.factory_core_manager.repository;


import org.example.factory_core_manager.entity.TransAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransActionRepository extends JpaRepository<TransAction, Long> {

    @Query("SELECT t FROM TransAction t " +
            "WHERE t.annualDetail.worker.id = :workerId " +
            "AND t.date = :date")
    List<TransAction> findSpecificTransActionByTime(@Param("workerId") Long workerId,
                                                    @Param("date") LocalDate date);
}
