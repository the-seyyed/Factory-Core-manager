package org.example.factory_core_manager.repository;


import org.example.factory_core_manager.entity.Attendance;
import org.example.factory_core_manager.entity.Profile;
import org.example.factory_core_manager.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Worker getWorkerById(Long id);

    void deleteWorkerByProfile_WorkerCode(String profileWorkerCode);

    void deleteWorkerByProfile(Profile profile);

    @Modifying
    @Transactional
    @Query("delete from Worker w where w.profile.workerCode =:workerCode ")
    void deleteWorkerByWorkerCode(@Param("workerCode") String workercode);


    @Query("select a from Attendance a join a.worker as w where w.id=:workerId and a.dateTime=:dateTime ")
    public List<Attendance> getAllAttendancesGreaterThan(@Param("workerId") Long workerId, @Param("dateTime") LocalDate dateTime);



}
