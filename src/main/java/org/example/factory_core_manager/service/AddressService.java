package org.example.factory_core_manager.service;


import org.example.factory_core_manager.entity.Worker;
import org.example.factory_core_manager.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {


    private WorkerRepository workerRepository;


    @Autowired
    public AddressService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }



}
