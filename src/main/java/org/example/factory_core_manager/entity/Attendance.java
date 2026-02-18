package org.example.factory_core_manager.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private LocalTime checkInTime;

    private LocalTime vacationInTime;
    private LocalTime vacationOutTime;

    private LocalTime checkOutTime;




    private LocalDate dateTime ;

    public Attendance( LocalTime checkInTime, LocalDate dateTime) {
        this.checkInTime = checkInTime;
        this.dateTime = dateTime;

    }


    @Transient
    public boolean isWorking() {
        return checkOutTime==null ;
    }

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;




}
