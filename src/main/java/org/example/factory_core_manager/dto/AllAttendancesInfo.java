package org.example.factory_core_manager.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
public class AllAttendancesInfo {

    private LocalTime checkInTime;
    private LocalTime checkOutTime;

    private CustomPersianDate persianDate ;

    private LocalTime vacationInTime;
    private LocalTime vacationOutTime;
}
