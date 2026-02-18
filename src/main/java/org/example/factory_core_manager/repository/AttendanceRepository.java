package org.example.factory_core_manager.repository;


import org.example.factory_core_manager.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("select a from Attendance as a join a.worker w where w.id=:id and a.dateTime=:dateTime")
    public List<Attendance> findAttendanceByDateTimeAndWorkerId(@Param("dateTime") LocalDate dateTime, @Param("id") Long id);


}
