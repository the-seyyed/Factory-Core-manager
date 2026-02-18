package org.example.factory_core_manager.service;

import jakarta.transaction.Transactional;
import org.example.factory_core_manager.convertor.Convertor;
import org.example.factory_core_manager.dto.AnnualDetailMenu;
import org.example.factory_core_manager.entity.AnnualDetail;
import org.example.factory_core_manager.entity.Worker;
import org.example.factory_core_manager.exception.WorkerNotFoundByIdException;
import org.example.factory_core_manager.repository.AnnualDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnualDetailService {

    private AnnualDetailRepository annualDetailRepository;

    private Convertor convertor;

    private WorkerService workerService;

    @Autowired
    public AnnualDetailService(AnnualDetailRepository annualDetailRepository , Convertor convertor , WorkerService workerService) {
        this.annualDetailRepository = annualDetailRepository;
        this.convertor = convertor;
        this.workerService = workerService;
    }

    public AnnualDetailMenu getAnnualDetail(Long workerId) {

        return convertor.AnnualDetailToAnnualDetailMenu(
                this.getOrCreateAnnualDetail(workerId));
    }


    public AnnualDetail getOrCreateAnnualDetail(Long workerId) {

        Worker worker = workerService.getWorkerById(workerId).orElseThrow(() ->
                new WorkerNotFoundByIdException("Worker not found by id: " + workerId));

        if(worker.getAnnualDetail() == null) {
            AnnualDetail annualDetail = new AnnualDetail();
            annualDetail.setTotalLeaves(0);
            annualDetail.setTotalAdvance(0f);
            annualDetail.setTotalLoan(0f);
            annualDetail.setWorker(worker);
            return annualDetailRepository.save(annualDetail);
        }
        return worker.getAnnualDetail();
    }





    @Scheduled(cron = "0 0 0 21 3 *", zone = "Asia/Tehran")
    public void resetWorkersAnnualDetailInfo(){

        workerService.findAllWorkers().forEach(worker -> {

            AnnualDetail annualDetail = getOrCreateAnnualDetail(worker.getId());
            annualDetail.setTotalLeaves(0);
            annualDetail.setTotalAdvance(0f);
            annualDetail.setTotalLoan(0f);
            annualDetailRepository.save(annualDetail);

        });


    }

}
