package org.example.factory_core_manager.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.example.factory_core_manager.convertor.Convertor;
import org.example.factory_core_manager.dto.AllWorkerInfo;
import org.example.factory_core_manager.dto.GetHomePageWorkers;
import org.example.factory_core_manager.dto.SaveWorkerInfo;
import org.example.factory_core_manager.dto.SaveWorkerProfile;
import org.example.factory_core_manager.entity.Attendance;
import org.example.factory_core_manager.entity.Profile;
import org.example.factory_core_manager.entity.Worker;
import org.example.factory_core_manager.exception.WorkerCodeIsDuplicatedException;
import org.example.factory_core_manager.exception.WorkerNotFoundByIdException;
import org.example.factory_core_manager.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;

    private final Convertor convertor;

    private final ProfileService profileService;

    EntityManager entityManager;


    @Autowired
    public WorkerService(WorkerRepository workerRepository,
                         Convertor convertor,
                         ProfileService profileService, EntityManager entityManager) {


        this.workerRepository = workerRepository;
        this.convertor = convertor;
        this.profileService = profileService;
        this.entityManager = entityManager;
    }


    public Optional<Worker> getWorkerById(Long id) {

        return workerRepository.findById(id);


    }


//    public Optional<Worker> getWorkerByWorkerCode(String workerCode) {
//
//        profileService.getProfileByWorkerCode(workerCode);
//    }


    public void saveWorker(Worker worker) {
        workerRepository.save(worker);
    }


    public void addNewWorker(AllWorkerInfo allWorkerInfo) {
        if (!profileService.isDuplicatedWorkerCode(allWorkerInfo.getWorkerCode())) {
            Worker worker = convertor.allWorkerInfoToWorker(allWorkerInfo);
            Profile workerProfile = convertor.allWorkerInfoToProfile(allWorkerInfo);
            worker.setProfile(workerProfile);
            workerRepository.save(worker);
        } else {
            throw new WorkerCodeIsDuplicatedException(
                    "worker code by " + allWorkerInfo.getWorkerCode() + " already exists");
        }
    }

    //    fetch profile is eager we have to try it on lazy and use entity graph too ;
    public List<AllWorkerInfo> getAllWorkers() {

        List<AllWorkerInfo> allWorkerInfoList = new ArrayList<>();

        List<Worker> allWorkers = workerRepository.findAll();

        allWorkers.forEach(worker -> {

            AllWorkerInfo allWorkerInfo = convertor.workerToAllWorkerInfo(worker);

            convertor.profileToAllWorkerInfo(worker.getProfile(), allWorkerInfo);

            allWorkerInfo.setAge(worker.getProfile().getAge());

            allWorkerInfoList.add(allWorkerInfo);

        });

        return allWorkerInfoList;


    }

    public void deleteWorkerByWorkerCode(String workerCode) {

        Worker worker = this.getWorkerByWorkerCode(workerCode);

        workerRepository.delete(worker);
    }

    public List<GetHomePageWorkers> getHomePageWorkers() {

        List<GetHomePageWorkers> workers = new ArrayList<>();

        workerRepository.findAll().forEach(worker -> {
            GetHomePageWorkers getHomePageWorkers = convertor.WorkerToHomePageWorker(worker);
            workers.add(getHomePageWorkers);

        });
        return workers;
    }

    public Worker getWorkerByWorkerCode(String workerCode) {
        Profile profileByWorkerCode = profileService.getProfileByWorkerCode(workerCode);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Worker> cq = cb.createQuery(Worker.class);

        Root<Worker> root = cq.from(Worker.class);

        List<Worker> workers = entityManager.createQuery(cq.where(cb.equal(
                        root.get("profile").get("workerCode"), workerCode))).
                getResultList();

        if (workers.isEmpty()) {
            throw new WorkerNotFoundByIdException("worker not found by code " + workerCode);
        } else if (workers.size() > 1) {
            throw new WorkerCodeIsDuplicatedException("worker code by " + workerCode + " is multiple");
        }
        System.out.println(workerCode);
        return workers.get(0);
    }

    public boolean existWorkerByWorkerId(Long workerId) {
        return workerRepository.existsById(workerId);
    }


//    public List<Attendance> getAllGreaterAttendancesThan(Long id , LocalDate date){
//        if(!this.existWorkerByWorkerId(id))
//            throw new WorkerNotFoundByIdException("worker not found by id " + id);
//
//        return workerRepository.getAllAttendancesGreaterThan(id, date);
//
//    }


    public List<Worker> findAllWorkers() {
        return workerRepository.findAll();
    }






}