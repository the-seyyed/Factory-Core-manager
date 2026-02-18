package org.example.factory_core_manager.controller;


import org.example.factory_core_manager.dto.AllWorkerInfo;
import org.example.factory_core_manager.dto.GetHomePageWorkers;
import org.example.factory_core_manager.dto.SaveWorkerInfo;
import org.example.factory_core_manager.dto.SaveWorkerProfile;
import org.example.factory_core_manager.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workerManaging")
public class WorkerController {

    private WorkerService workerService;


    @Autowired
    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @PostMapping("/addNewWorker")
    public ResponseEntity<String> addNewWorker(
            @RequestBody AllWorkerInfo allWorkerInfo) {

        workerService.addNewWorker(allWorkerInfo);
        return new ResponseEntity<>("Worker added successfully", HttpStatus.OK);

    }

    @GetMapping("/getAllWorkers")
    public List<AllWorkerInfo> getAllWorkers() {
        return workerService.getAllWorkers();
    }

    @DeleteMapping("/deleteWorker/{workerCode}")
    public ResponseEntity<String> deleteWorker(@PathVariable String workerCode) {
        workerService.deleteWorkerByWorkerCode(workerCode);
        return new ResponseEntity<>("Worker deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/get_homepage_workers")
    public List<GetHomePageWorkers> getHomepageWorkers() {
        return workerService.getHomePageWorkers();
    }

}
