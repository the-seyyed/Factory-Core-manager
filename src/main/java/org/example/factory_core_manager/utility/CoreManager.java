package org.example.factory_core_manager.utility;

import com.github.mfathi91.time.PersianDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.factory_core_manager.dto.DailyWorkedDetailedInfo;
import org.example.factory_core_manager.dto.GetOverAndUnderWorkCosts;
import org.example.factory_core_manager.dto.MonthlyWorkedDetailedInfo;
import org.example.factory_core_manager.dto.PaymentDetailsDegree;
import org.example.factory_core_manager.entity.Attendance;
import org.example.factory_core_manager.entity.Profile;
import org.example.factory_core_manager.entity.Worker;
import org.example.factory_core_manager.enumeration.PersianMonth;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CoreManager {





    public DailyWorkedDetailedInfo calculateDetailedStructureOfAttendances(Attendance attendance) {


        DailyWorkedDetailedInfo dailyWorkedDetailedInfo = new DailyWorkedDetailedInfo();

        dailyWorkedDetailedInfo.setCheckInTime(attendance.getCheckInTime());

        dailyWorkedDetailedInfo.setCheckOutTime(attendance.getCheckOutTime());

        dailyWorkedDetailedInfo.setOfficialWorkedMinute(this.getOfficialWorkedTimeInMinutes(attendance));

        dailyWorkedDetailedInfo.setOverWorkedMinute(this.getOverWorkedTimeInMinutes(attendance));

        dailyWorkedDetailedInfo.setUnderWorkedMinute(this.getUnderWorkedTimeInMinutes(attendance));

        return dailyWorkedDetailedInfo;

    }


    public Long getOfficialWorkedTimeInMinutes(Attendance attendance) {


        if (attendance.getCheckOutTime() != null) {

            LocalTime checkInTime = attendance.getCheckInTime();

            if (checkInTime.isBefore(LocalTime.of(7, 30)))

                checkInTime = LocalTime.of(7, 30);

            LocalTime checkOutTime = attendance.getCheckOutTime();

            if (checkOutTime.isAfter(LocalTime.of(16, 5)))
                checkOutTime = LocalTime.of(16, 5);


            Duration duration = Duration.between(checkInTime, checkOutTime);

            System.out.println(duration.toMinutes());

            return duration.toMinutes();

        }
        return -1L;

    }

    public Long getOverWorkedTimeInMinutes(Attendance attendance) {
        if (attendance.getCheckOutTime() != null) {
            if (!attendance.getCheckOutTime().isAfter(LocalTime.of(16, 5)))
                return 0L;

            Duration duration = Duration.between(LocalTime.of(16, 5), attendance.getCheckOutTime());

            return duration.toMinutes();

        }

        return -1L;

    }

    public Long getUnderWorkedTimeInMinutes(Attendance attendance) {

        if (attendance.getCheckOutTime() != null) {

            Duration enterUnderWorked = Duration.ofMinutes(0L);

            Duration exitUnderWorked = Duration.ofMinutes(0L);

            Long totalUnderWorked = 0L;

            if (attendance.getCheckInTime().isAfter(LocalTime.of(7, 30))) {

                enterUnderWorked = Duration.between(LocalTime.of(7, 30), attendance.getCheckInTime());

                totalUnderWorked += enterUnderWorked.toMinutes();

            }
            if (attendance.getCheckOutTime().isBefore(LocalTime.of(16, 5))) {
                exitUnderWorked = Duration.between(attendance.getCheckOutTime(), LocalTime.of(16, 5));

                totalUnderWorked += exitUnderWorked.toMinutes();


            }


            return totalUnderWorked;

        }
        return -1L;
    }

    public Long getTotalWorkedTimeInMinutes(Attendance attendance) {
        return this.getOverWorkedTimeInMinutes(attendance) + this.getOfficialWorkedTimeInMinutes(attendance);
    }


    public static void main(String[] args) {

        CoreManager manager = new CoreManager();

        Attendance attendance = new Attendance();

        attendance.setCheckInTime(LocalTime.of(7, 31));

        attendance.setCheckOutTime(LocalTime.of(17, 5));

        System.out.println(manager.getOfficialWorkedTimeInMinutes(attendance));

        System.out.println(manager.getOverWorkedTimeInMinutes(attendance));

        System.out.println(manager.getOfficialWorkedTimeInMinutes(attendance) + manager.getOverWorkedTimeInMinutes(attendance));


    }

    public PaymentDetailsDegree getDifferentSidesOfPaymentForAWorker(
                                                                      float backGroundYearsPaymentPerYear,
                                                                      float marriedPayment,
                                                                      float havingAChildPayment,
                                                                      float havingChildrenPayment,
                                                                      Worker worker) {

        PaymentDetailsDegree paymentDetails = new PaymentDetailsDegree(
                0, 0, 0, 0);
        Profile profile = worker.getProfile();
        int backGroundYears = profile.getBackGroundYears();
        boolean havingHome = profile.isHasHome();
        boolean isMarried = profile.isMarried();
        int childNumber = profile.getChildrenNumber();

        if (backGroundYears > 0)
            paymentDetails.setPaymentForBackGroundYears(backGroundYearsPaymentPerYear * backGroundYears);

        if (childNumber > 0) {
            paymentDetails.setPaymentForNumbersOfChildren(
                    childNumber == 1 ? havingAChildPayment : havingChildrenPayment
            );
        }


        if (isMarried)
            paymentDetails.setPaymentForIfMarried(marriedPayment);

        if (havingHome)
            paymentDetails.setPaymentForHavingHome(havingAChildPayment);

        return paymentDetails;
    }

    public List<LocalDate> getLocalDateFromPersianMonthEnum(PersianMonth persianMonth) {
        int year = PersianDate.now().getYear();
        int month = persianMonth.getMonthNumber();

        PersianDate startPersianDate = PersianDate.of(year, month, 1);
        PersianDate endPersianDate = PersianDate.of(year, month, persianMonth.getDays(year));

        return List.of(
                startPersianDate.toGregorian(),
                endPersianDate.toGregorian()
        );
    }





}


