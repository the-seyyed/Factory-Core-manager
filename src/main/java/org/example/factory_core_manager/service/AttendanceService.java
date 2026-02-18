package org.example.factory_core_manager.service;

import com.github.mfathi91.time.PersianDate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.example.factory_core_manager.convertor.Convertor;
import org.example.factory_core_manager.dto.*;
import org.example.factory_core_manager.entity.Attendance;
import org.example.factory_core_manager.entity.PaymentDegree;
import org.example.factory_core_manager.entity.Worker;
import org.example.factory_core_manager.enumeration.PersianMonth;
import org.example.factory_core_manager.exception.*;
import org.example.factory_core_manager.repository.AttendanceRepository;
import org.example.factory_core_manager.utility.CoreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final EntityManager entityManager;

    private final WorkerService workerService;

    private final PaymentDegreeService paymentDegreeService;

    private final CoreManager coreManager;

    private Convertor convertor;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository, WorkerService workerService, EntityManager entityManager, Convertor convertor , PaymentDegreeService paymentDegreeService) {
        this.attendanceRepository = attendanceRepository;
        this.workerService = workerService;
        this.entityManager = entityManager;
        this.convertor = convertor;
        coreManager = new CoreManager();
        this.paymentDegreeService = paymentDegreeService;
    }

    public void setCheckInTimeForWorker(Long workerId, LocalTime checkInTime) {

        Optional<Worker> workerR = workerService.getWorkerById(workerId);

        if (!attendanceRepository.findAttendanceByDateTimeAndWorkerId(LocalDate.now(), workerId).isEmpty()) {
            throw new WorkerAlreadyCheckedInException("worker already checked in");
        }

        if (workerR.isEmpty()) {
            throw new WorkerNotFoundByIdException("Worker not found by id: " + workerId);
        }

        Attendance attendance = new Attendance();

        attendance.setCheckInTime(checkInTime.withNano(0));

        attendance.setDateTime(LocalDate.now());

        attendance.setWorker(workerR.get());

        Set<Attendance> attendances = workerR.get().getAttendances();

        attendances.add(attendance);

        workerR.get().setAttendances(attendances);

        workerService.saveWorker(workerR.get());


    }

    public void setCheckOutTimeForWorker(Long id, LocalTime checkOutTime) {

        Optional<Worker> workerR = Optional.
                ofNullable(workerService.getWorkerById(id)).
                orElseThrow(() -> new WorkerNotFoundByIdException("Worker not found by id: " + id));

        List<Attendance> attendances = this.attendanceRepository.findAttendanceByDateTimeAndWorkerId(LocalDate.now(), id);

        if (attendances.isEmpty()) {
            throw new WorkerNotStartedToWorkException("worker not started");
        }
        if (!(attendances.get(0).isWorking())) {
            throw new DuplicateRequestForCheckOutException("worker already checked out ");
        }

        if(attendances.getFirst().getVacationInTime()!=null &&
                attendances.getFirst().getVacationOutTime()==null)
            throw new WorkerNotCorrectlyStartedFinishedVacation("bad request for setting check-out time before ");

        attendances.get(0).setCheckOutTime(checkOutTime.withNano(0));
        workerService.saveWorker(workerR.get());

    }


    public List<AllAttendancesInfo> getAllGreaterAttendancesThan(Long id, String persiandate) {

        PersianDate newPersianDate = PersianDate.parse(persiandate);

        LocalDate localDate = newPersianDate.toGregorian();

        System.out.println(localDate);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Attendance> attendanceCriteriaQuery = cb.createQuery(Attendance.class);

        Root<Attendance> attendanceRoot = attendanceCriteriaQuery.from(Attendance.class);

        Predicate greaterThanPredicate = cb.greaterThan(attendanceRoot.get("dateTime"), localDate);

        Predicate equalIdPredicate = cb.equal(attendanceRoot.get("worker").get("id"), id);

        List<AllAttendancesInfo> allAttendancesInfos = new ArrayList<>();

        entityManager.createQuery(attendanceCriteriaQuery.where(greaterThanPredicate, equalIdPredicate)).getResultList().forEach(
                attendance -> {


                    AllAttendancesInfo allAttendancesInfo = new AllAttendancesInfo();

                    CustomPersianDate customPersianDate = new CustomPersianDate();

                    PersianDate persianDate = PersianDate.fromGregorian(attendance.getDateTime());

                    customPersianDate.setDay(persianDate.getDayOfMonth());

                    customPersianDate.setYear(persianDate.getYear());

                    customPersianDate.setMonth(persianDate.getMonthValue());

                    allAttendancesInfo.setPersianDate(customPersianDate);



                    if(attendance.getCheckInTime()!=null)
                        allAttendancesInfo.setCheckOutTime(attendance.getCheckOutTime());

                    else
                        attendance.setCheckInTime(LocalTime.of(0,0,0));

                    if(attendance.getCheckOutTime()!=null)
                        allAttendancesInfo.setCheckInTime(attendance.getCheckInTime());
                    else
                        attendance.setCheckOutTime(LocalTime.of(0,0,0));

                    if (attendance.getVacationInTime()!=null)
                        allAttendancesInfo.setVacationInTime(attendance.getVacationInTime().withNano(0));
                    else
                        attendance.setVacationInTime(LocalTime.of(0,0,0).withNano(0));

                    if(attendance.getVacationOutTime()!=null)
                        allAttendancesInfo.setVacationOutTime(attendance.getVacationOutTime().withNano(0));
                    else
                        attendance.setVacationOutTime(LocalTime.of(0,0,0).withNano(0));

                    allAttendancesInfos.add(allAttendancesInfo);


                });

        return allAttendancesInfos;


    }


    public List<DailyWorkedDetailedInfo> getAllDailyWorkedInfoBySpecialDateTime(Long workerId, String persiandate) {

        PersianDate newPersianDate = PersianDate.parse(persiandate);

        LocalDate localDate = newPersianDate.toGregorian();

        System.out.println(localDate);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Attendance> attendanceCriteriaQuery = cb.createQuery(Attendance.class);

        Root<Attendance> attendanceRoot = attendanceCriteriaQuery.from(Attendance.class);

        Predicate greaterThanPredicate = cb.greaterThan(attendanceRoot.get("dateTime"), localDate);

        Predicate equalIdPredicate = cb.equal(attendanceRoot.get("worker").get("id"), workerId);

        List<DailyWorkedDetailedInfo> dailyWorkedDetailedInfos = new ArrayList<>();

        List<Attendance> allAttendancesInfos = entityManager.
                createQuery(attendanceCriteriaQuery.where(greaterThanPredicate, equalIdPredicate)).getResultList();

        allAttendancesInfos.forEach(attendance -> {

            DailyWorkedDetailedInfo dailyWorkedDetailedInfo = coreManager.calculateDetailedStructureOfAttendances(attendance);

            CustomPersianDate customPersianDate = new CustomPersianDate();

            customPersianDate.setYear(PersianDate.fromGregorian(attendance.getDateTime()).getYear());

            customPersianDate.setMonth(PersianDate.fromGregorian(attendance.getDateTime()).getMonthValue());

            customPersianDate.setDay(PersianDate.fromGregorian(attendance.getDateTime()).getDayOfMonth());

            dailyWorkedDetailedInfo.setCustomPersianDate(customPersianDate);

            dailyWorkedDetailedInfos.add(dailyWorkedDetailedInfo);

        });
        return dailyWorkedDetailedInfos;

    }
    public MonthlyWorkedDetailedInfo getAllDailyWorkedInfoBySpecialDateTimeTest(
            Long workerId, String persianDate , GetOverAndUnderWorkCosts overAndUnderWorkCosts) {

        float overWorkedCostPerMinute = overAndUnderWorkCosts.getOverTimeCostPerHour()/60;

        float underWorkedCostPerMinute = overAndUnderWorkCosts.getUnderWorkCostPerMinute();

        MonthlyWorkedDetailedInfo monthlyWorkedDetailedInfo = new MonthlyWorkedDetailedInfo();

        PersianDate newPersianDate = PersianDate.parse(persianDate);

        LocalDate localDate = newPersianDate.toGregorian();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Attendance> attendanceCriteriaQuery = cb.createQuery(Attendance.class);

        Root<Attendance> attendanceRoot = attendanceCriteriaQuery.from(Attendance.class);

        Predicate greaterThanPredicate = cb.greaterThan(attendanceRoot.get("dateTime"), localDate);

        Predicate equalIdPredicate = cb.equal(attendanceRoot.get("worker").get("id"), workerId);

        List<DailyWorkedDetailedInfo> dailyWorkedDetailedInfos = new ArrayList<>();

        List<Attendance> allAttendancesInfos = entityManager.
                createQuery(attendanceCriteriaQuery.where(greaterThanPredicate, equalIdPredicate)).getResultList();
        float allAmount = 0 ;
        Long allOverWorkedTime = 0L ;
        Long allUnderWorkedTime = 0L ;
        Long allOfficialWorkedTime = 0L ;
        float allOverWorkedTimeCost = 0 ;
        float allUnderWorkedTimeCost = 0 ;
        float allOfficialWorkedTimeCost = 0 ;


        for (Attendance attendance : allAttendancesInfos) {


            DailyWorkedDetailedInfo dailyWorkedDetailedInfo = coreManager.calculateDetailedStructureOfAttendances(attendance);

            allAmount = allAmount + dailyWorkedDetailedInfo.getIncomeOfDay();

            allOverWorkedTime+=dailyWorkedDetailedInfo.getOverWorkedMinute();

            allUnderWorkedTime+=dailyWorkedDetailedInfo.getUnderWorkedMinute();

            allOfficialWorkedTime+=dailyWorkedDetailedInfo.getOfficialWorkedMinute();

            CustomPersianDate customPersianDate = new CustomPersianDate();

            customPersianDate.setYear(PersianDate.fromGregorian(attendance.getDateTime()).getYear());

            customPersianDate.setMonth(PersianDate.fromGregorian(attendance.getDateTime()).getMonthValue());

            customPersianDate.setDay(PersianDate.fromGregorian(attendance.getDateTime()).getDayOfMonth());

            dailyWorkedDetailedInfo.setCustomPersianDate(customPersianDate);

            dailyWorkedDetailedInfo.setUnderWorkedCost(underWorkedCostPerMinute*dailyWorkedDetailedInfo.getUnderWorkedMinute());

            dailyWorkedDetailedInfo.setOverWorkedCost(overWorkedCostPerMinute*dailyWorkedDetailedInfo.getOverWorkedMinute());

            allUnderWorkedTimeCost+=dailyWorkedDetailedInfo.getUnderWorkedCost();

            allOverWorkedTimeCost+=dailyWorkedDetailedInfo.getOverWorkedCost();

            allOfficialWorkedTimeCost+=allOverWorkedTimeCost-allUnderWorkedTimeCost;

            dailyWorkedDetailedInfos.add(dailyWorkedDetailedInfo);
        }




        monthlyWorkedDetailedInfo.setAllAmount(allAmount);
        monthlyWorkedDetailedInfo.setAllOverWorkedTime(allOverWorkedTime);
        monthlyWorkedDetailedInfo.setAllUnderWorkedTime(allUnderWorkedTime);
        monthlyWorkedDetailedInfo.setAllOfficialWorkedTime(allOfficialWorkedTime);
        monthlyWorkedDetailedInfo.setAllOverWorkedTimeCost(allOverWorkedTimeCost);
        monthlyWorkedDetailedInfo.setAllUnderWorkedTimeCost(allUnderWorkedTimeCost);
        monthlyWorkedDetailedInfo.setAllOfficialWorkedTimeCost(allOfficialWorkedTimeCost);
        monthlyWorkedDetailedInfo.setDailyWorkedDetailedInfoList(dailyWorkedDetailedInfos);
        return monthlyWorkedDetailedInfo;


    }


    public MonthlyWorkedDetailedInfo getAllDailyWorkedInfoBySpecialDateTimeWithLimiter(
            Long workerId, int persianMonthNumber, GetOverAndUnderWorkCosts overAndUnderWorkCosts) {


        PaymentDegree paymentDegree = paymentDegreeService.getThePaymentDegree();
        float backGroundYearsPaymentPerYear=paymentDegree.getBackGroundYearsPaymentPerYear();
        float marriedPayment= paymentDegree.getMarriedPayment();
        float havingAChildPayment= paymentDegree.getHavingAChildPayment();
        float havingChildrenPayment = paymentDegree.getHavingChildrenPayment();

        float overWorkedCostPerMinute = overAndUnderWorkCosts.getOverTimeCostPerHour()/60;

        float underWorkedCostPerMinute = overAndUnderWorkCosts.getUnderWorkCostPerMinute();

        PersianMonth persianMonth = PersianMonth.getPersianMonth(persianMonthNumber);

        MonthlyWorkedDetailedInfo monthlyWorkedDetailedInfo = new MonthlyWorkedDetailedInfo();

        LocalDate startDate = coreManager.getLocalDateFromPersianMonthEnum(persianMonth).getFirst();
        LocalDate endDate = coreManager.getLocalDateFromPersianMonthEnum(persianMonth).getLast();



        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Attendance> attendanceCriteriaQuery = cb.createQuery(Attendance.class);

        Root<Attendance> attendanceRoot = attendanceCriteriaQuery.from(Attendance.class);

        Predicate greaterThanPredicate = cb.greaterThan(attendanceRoot.get("dateTime"), startDate);

        Predicate lessThanPredicate = cb.lessThan(attendanceRoot.get("dateTime"), endDate);

        Predicate equalIdPredicate = cb.equal(attendanceRoot.get("worker").get("id"), workerId);

        List<DailyWorkedDetailedInfo> dailyWorkedDetailedInfos = new ArrayList<>();

        List<Attendance> allAttendancesInfos = entityManager.
                createQuery(attendanceCriteriaQuery.where(greaterThanPredicate,lessThanPredicate, equalIdPredicate)).getResultList();
        float allAmount = 0 ;
        Long allOverWorkedTime = 0L ;
        Long allUnderWorkedTime = 0L ;
        Long allOfficialWorkedTime = 0L ;
        float allOverWorkedTimeCost = 0 ;
        float allUnderWorkedTimeCost = 0 ;
        float allOfficialWorkedTimeCost = 0 ;


        for (Attendance attendance : allAttendancesInfos) {


            DailyWorkedDetailedInfo dailyWorkedDetailedInfo = coreManager.calculateDetailedStructureOfAttendances(attendance);

            allAmount = allAmount + dailyWorkedDetailedInfo.getIncomeOfDay();

            allOverWorkedTime+=dailyWorkedDetailedInfo.getOverWorkedMinute();

            allUnderWorkedTime+=dailyWorkedDetailedInfo.getUnderWorkedMinute();

            allOfficialWorkedTime+=dailyWorkedDetailedInfo.getOfficialWorkedMinute();

            CustomPersianDate customPersianDate = new CustomPersianDate();

            customPersianDate.setYear(PersianDate.fromGregorian(attendance.getDateTime()).getYear());

            customPersianDate.setMonth(PersianDate.fromGregorian(attendance.getDateTime()).getMonthValue());

            customPersianDate.setDay(PersianDate.fromGregorian(attendance.getDateTime()).getDayOfMonth());

            dailyWorkedDetailedInfo.setCustomPersianDate(customPersianDate);

            dailyWorkedDetailedInfo.setUnderWorkedCost(underWorkedCostPerMinute*dailyWorkedDetailedInfo.getUnderWorkedMinute());

            dailyWorkedDetailedInfo.setOverWorkedCost(overWorkedCostPerMinute*dailyWorkedDetailedInfo.getOverWorkedMinute());

            allUnderWorkedTimeCost+=dailyWorkedDetailedInfo.getUnderWorkedCost();

            allOverWorkedTimeCost+=dailyWorkedDetailedInfo.getOverWorkedCost();

            allOfficialWorkedTimeCost+=allOverWorkedTimeCost-allUnderWorkedTimeCost;

            dailyWorkedDetailedInfos.add(dailyWorkedDetailedInfo);
        }

        Optional<Worker> workerById = workerService.getWorkerById(workerId);
        PaymentDetailsDegree paymentDetailsDegree = coreManager.getDifferentSidesOfPaymentForAWorker(
                backGroundYearsPaymentPerYear ,
                marriedPayment ,
                havingAChildPayment ,
                havingChildrenPayment ,
                workerById.get());

        monthlyWorkedDetailedInfo.setAllAmount(allAmount);
        monthlyWorkedDetailedInfo.setAllOverWorkedTime(allOverWorkedTime);
        monthlyWorkedDetailedInfo.setAllUnderWorkedTime(allUnderWorkedTime);
        monthlyWorkedDetailedInfo.setAllOfficialWorkedTime(allOfficialWorkedTime);
        monthlyWorkedDetailedInfo.setAllOverWorkedTimeCost(allOverWorkedTimeCost);
        monthlyWorkedDetailedInfo.setAllUnderWorkedTimeCost(allUnderWorkedTimeCost);
        monthlyWorkedDetailedInfo.setAllOfficialWorkedTimeCost(allOfficialWorkedTimeCost);
        monthlyWorkedDetailedInfo.setDailyWorkedDetailedInfoList(dailyWorkedDetailedInfos);
        monthlyWorkedDetailedInfo.setBackGroundPayment(paymentDetailsDegree.getPaymentForBackGroundYears());
        monthlyWorkedDetailedInfo.setMarriagePayment(paymentDetailsDegree.getPaymentForIfMarried());
        monthlyWorkedDetailedInfo.setHavingChildrenPayment(paymentDetailsDegree.getPaymentForNumbersOfChildren());
        return monthlyWorkedDetailedInfo;


    }




    public void setVacationInTime (Long workerId , LocalDate date ) {

        Optional<Worker> worker = workerService.getWorkerById(workerId);
        List<Attendance> attendanceList = attendanceRepository.findAttendanceByDateTimeAndWorkerId(LocalDate.now(), workerId);

        if (worker.isEmpty()) {
            throw new WorkerNotFoundByIdException("Worker not found by id : "+workerId);
        }

        if (attendanceList.isEmpty()){
            throw new WorkerNotStartedToWorkException("worker not started to work to set a vacation");
        }

        if (attendanceList.size()>1) {
            throw new LogicalRuntimeException("there are more than one active attendance for this worker by a special date time ");
        }



        Attendance attendance = attendanceList.get(0);

        if(attendance.getVacationInTime()!=null){
            throw new VacationInTimeIsAlreadyUsed("vacationInTime is already used by the time : "+attendance.getVacationInTime());
        }
        if(attendance.getCheckOutTime()!=null){
            throw new WorkerHasCheckedOutException("worker has checked out time : "+attendance.getCheckOutTime());
        }
        attendance.setVacationInTime(LocalTime.now().withNano(0));
        attendanceRepository.save(attendance);

    }

    public void setVacationOutTime (Long workerId , LocalDate date ) {
        Optional<Worker> worker = workerService.getWorkerById(workerId);

        List<Attendance> attendanceList = attendanceRepository.findAttendanceByDateTimeAndWorkerId(LocalDate.now(), workerId);

        if (worker.isEmpty()) {
            throw new WorkerNotFoundByIdException("Worker not found by id : "+workerId);
        }

        if (attendanceList.isEmpty()){
            throw new WorkerNotStartedToWorkException("worker not started to work to set a vacation");
        }

        if (attendanceList.size()>1) {
            throw new LogicalRuntimeException("there are more than one active attendance for this worker by a special date time ");
        }



        Attendance attendance = attendanceList.get(0);

        if(attendance.getVacationInTime()==null){
            throw new WorkerNotGetVacationInTime("worker not get vacation in time by time :" +date);
        }

        if(attendance.getVacationOutTime()!=null){
            throw new VacationOutTimeAlreadyUsed("vacation out time is already set by time : " + attendance.getVacationOutTime());
        }

        if(attendance.getCheckOutTime()!=null){
            throw new WorkerHasCheckedOutException("worker has checked out time : " + attendance.getCheckOutTime());
        }
        attendance.setVacationOutTime(LocalTime.now().withNano(0));
        attendanceRepository.save(attendance);
    }

}
