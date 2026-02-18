package org.example.factory_core_manager.controller;

import org.example.factory_core_manager.dto.AllAttendancesInfo;
import org.example.factory_core_manager.dto.GetOverAndUnderWorkCosts;
import org.example.factory_core_manager.dto.MonthlyWorkedDetailedInfo;
import org.example.factory_core_manager.enumeration.PersianMonth;
import org.example.factory_core_manager.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/attendanceManaging")
public class AttendanceController {


    private final AttendanceService attendanceService;


    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/check_in_attendance/{workerId}")
    public ResponseEntity<String> addCheckInAttendance(@PathVariable long workerId) {

        attendanceService.setCheckInTimeForWorker(workerId, LocalTime.now());

        return new ResponseEntity<String>("added successfully", HttpStatus.OK);

    }

    @PostMapping("/check_out_attendance/{workerId}")
    public ResponseEntity<String> addCheckOutAttendance(@PathVariable long workerId) {
        attendanceService.setCheckOutTimeForWorker(workerId, LocalTime.now());
        return new ResponseEntity<>("added successfully", HttpStatus.OK);
    }

    @GetMapping("/getAllAttendanceInfoForSpecialTimeAndWorker/{workerId}/{date}")
    public List<AllAttendancesInfo> getAllAttendanceInfoForSpecialTimeAndWorker
            (@PathVariable long workerId, @PathVariable String date) {

        return attendanceService.getAllGreaterAttendancesThan(workerId, date);
    }


//    @PostMapping("/getAllDetailedWorkedInfoBySpecialTime/{workerId}/{date}")
//    public MonthlyWorkedDetailedInfo getAllDetailedWorkedInfoBySpecialTime
//            (@PathVariable long workerId,
//             @PathVariable String date,
//             @RequestBody GetOverAndUnderWorkCosts getOverAndUnderWorkCosts) {
//
//        return attendanceService.
//                getAllDailyWorkedInfoBySpecialDateTimeTest
//                        (workerId, date, getOverAndUnderWorkCosts);
//
//    }

    @PostMapping("/vacationIn/{workerId}")
    public void vacationInAttendance(@PathVariable long workerId) {
        attendanceService.setVacationInTime(workerId , LocalDate.now());
    }

    @PostMapping("/vacationOut/{workerId}")
    public void vacationOutAttendance(@PathVariable long workerId) {
        attendanceService.setVacationOutTime(workerId , LocalDate.now());
    }
    @PostMapping("/getMonthlyDetailedPayment/{workerId}/{persianMonthNumber}")
    public MonthlyWorkedDetailedInfo test
            (@PathVariable long workerId,
             @PathVariable int persianMonthNumber,
             @RequestBody GetOverAndUnderWorkCosts getOverAndUnderWorkCosts) {

        return attendanceService.
                getAllDailyWorkedInfoBySpecialDateTimeWithLimiter(workerId, persianMonthNumber,
                        getOverAndUnderWorkCosts);


    }

}
