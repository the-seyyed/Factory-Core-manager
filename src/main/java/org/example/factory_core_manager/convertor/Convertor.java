package org.example.factory_core_manager.convertor;


import com.github.mfathi91.time.PersianDate;
import org.example.factory_core_manager.dto.*;
import org.example.factory_core_manager.entity.*;
import org.hibernate.annotations.Comment;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class Convertor {

    private ModelMapper modelMapper;



    public Convertor() {

        modelMapper = new ModelMapper();

    }

    public Worker workerInfoSaveToWorker(SaveWorkerInfo saveWorkerInfo) {
        return modelMapper.map(saveWorkerInfo, Worker.class);
    }

    public Profile workerProfileInfoToProfile(SaveWorkerProfile saveWorkerProfile) {
        return modelMapper.map(saveWorkerProfile, Profile.class);
    }

    public Worker allWorkerInfoToWorker(AllWorkerInfo allWorkerInfo) {

        return modelMapper.map(allWorkerInfo, Worker.class);
    }


    public Profile allWorkerInfoToProfile(AllWorkerInfo allWorkerInfo) {

        return modelMapper.map(allWorkerInfo, Profile.class);
    }


    public AllWorkerInfo workerToAllWorkerInfo(Worker worker) {
        return modelMapper.map(worker, AllWorkerInfo.class);
    }



    public void profileToAllWorkerInfo(Profile profile , AllWorkerInfo allWorkerInfo) {
        modelMapper.map(profile, allWorkerInfo);
    }

    public GetHomePageWorkers WorkerToHomePageWorker(Worker worker) {
        return modelMapper.map(worker, GetHomePageWorkers.class);
    }

    public AllAttendancesInfo AttendanceToAllAttendances(Attendance attendance) {

        return modelMapper.map(attendance, AllAttendancesInfo.class);

    }

    public AnnualDetailMenu AnnualDetailToAnnualDetailMenu(AnnualDetail annualDetail) {
        return modelMapper.map(annualDetail, AnnualDetailMenu.class);
    }

    public TransAction transActionSaveToTransAction(TransActionSave transActionSave) {
        return modelMapper.map(transActionSave, TransAction.class);
    }

    public TransActionMenu transActionToTransActionMenu(TransAction transAction) {
        return modelMapper.map(transAction, TransActionMenu.class);
    }

    public CustomPersianDate persianDateToCustomPersianDate(PersianDate persianDate) {


        return modelMapper.map(persianDate, CustomPersianDate.class);

    }

    public PaymentDegree paymentDegreeSaveToPaymentDegree(PaymentDegreeSave paymentDegreeSave) {
        return modelMapper.map(paymentDegreeSave, PaymentDegree.class);
    }

    public PaymentDegreeMenu paymentDegreeToPaymentDegreeMenu(PaymentDegree paymentDegree) {
        return modelMapper.map(paymentDegree, PaymentDegreeMenu.class);
    }

    public Inventory saveInventoryDtoToInventory(SaveInventoryDto saveInventoryDto) {
        return modelMapper.map(saveInventoryDto, Inventory.class);
    }












    public static void main(String[] args) {
        Convertor convertor = new Convertor();

        Worker worker = new Worker();
        Profile profile = new Profile();
        worker.setFirstName("John");
        worker.setLastName("Doe");
        profile.setMarried(false);
        profile.setDateOfBirth(LocalDate.now());
        worker.setProfile(profile);
        AllWorkerInfo allWorkerInfo =convertor.workerToAllWorkerInfo(worker);
        convertor.profileToAllWorkerInfo(profile , allWorkerInfo);
        System.out.println(allWorkerInfo.isMarried());

    }

    public Product SaveProductDtoToProduct(SaveProductDto saveProductDto) {
        return modelMapper.map(saveProductDto, Product.class);
    }







}
