package org.example.factory_core_manager.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
public class DailyWorkedDetailedInfo {

    private CustomPersianDate customPersianDate;

    private LocalTime checkInTime ;

    private LocalTime checkOutTime ;

    private float incomeOfDay ;

    private Long officialWorkedMinute ;

    private Long overWorkedMinute ;

    private Long underWorkedMinute ;

    private float overWorkedCost ;

    private float underWorkedCost ;



}
