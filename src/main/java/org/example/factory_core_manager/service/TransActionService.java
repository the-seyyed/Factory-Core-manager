package org.example.factory_core_manager.service;
import com.github.mfathi91.time.PersianDate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.val;
import org.example.factory_core_manager.convertor.Convertor;
import org.example.factory_core_manager.dto.CustomPersianDate;
import org.example.factory_core_manager.dto.TransActionMenu;
import org.example.factory_core_manager.dto.TransActionSave;
import org.example.factory_core_manager.entity.AnnualDetail;
import org.example.factory_core_manager.entity.TransAction;
import org.example.factory_core_manager.entity.Worker;
import org.example.factory_core_manager.enumeration.WorkerTransActionType;
import org.example.factory_core_manager.exception.MultipleTransActionForSpecificDate;
import org.example.factory_core_manager.exception.WorkerNotFoundByIdException;
import org.example.factory_core_manager.repository.AnnualDetailRepository;
import org.example.factory_core_manager.repository.TransActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransActionService {

    private AnnualDetailService annualDetailService;

    private TransActionRepository transActionRepository;

    private WorkerService workerService;

    private EntityManager entityManager;

    private Convertor convertor;

    @Autowired
    public TransActionService(TransActionRepository transActionRepository,EntityManager entityManager, Convertor convertor, WorkerService workerService, AnnualDetailService annualDetailService) {
        this.transActionRepository = transActionRepository;
        this.convertor = convertor;
        this.workerService = workerService;
        this.annualDetailService = annualDetailService;
        this.entityManager = entityManager;
    }

    public void saveNewTransAction(TransActionSave transActionSave, Long WorkerId) {

        Optional<Worker> worker = workerService.getWorkerById(WorkerId);

        if (worker.isEmpty())
            throw new WorkerNotFoundByIdException("Worker not found by id : " + WorkerId);

        AnnualDetail annualDetail = annualDetailService.getOrCreateAnnualDetail(WorkerId);
        TransAction transAction = convertor.transActionSaveToTransAction(transActionSave);
        if (transActionSave.getType().ordinal() == WorkerTransActionType.LEAVE.ordinal()) {
            if (!getSpecificTransActionByTime(WorkerId , LocalDate.now()).isEmpty())
                throw new MultipleTransActionForSpecificDate("multiple leave transaction for specific date : " + LocalDate.now());
            annualDetail.setTotalLeaves(annualDetail.getTotalLeaves() + 1);
        } else if (transActionSave.getType().ordinal() == WorkerTransActionType.ADVANCE.ordinal()) {
            annualDetail.setTotalAdvance(annualDetail.getTotalAdvance() + transActionSave.getAmount());
        } else if (transActionSave.getType().ordinal() == WorkerTransActionType.LOAN.ordinal()) {
            annualDetail.setTotalLoan(annualDetail.getTotalLoan() + transActionSave.getAmount());
        }
        else {
            payTransActionBack(transActionSave , annualDetail);
        }
        System.out.println(transAction.getType().toString());
        transAction.setDate(LocalDate.now());
        transAction.setAnnualDetail(annualDetail);
        transActionRepository.save(transAction);
    }


    public List<TransActionMenu> getTransActionMenu(Long workerId) {

        Optional<Worker> worker = workerService.getWorkerById(workerId);

        if (worker.isEmpty())
            throw new WorkerNotFoundByIdException("Worker not found by id : " + workerId);

        List<TransAction> transActionList =
                worker.get().getAnnualDetail().getTransActions();
        List<TransActionMenu> transActionMenuList =new ArrayList<>();

        for (TransAction transAction : transActionList) {
            PersianDate persianDate = PersianDate.fromGregorian(transAction.getDate());
            CustomPersianDate customPersianDate = new CustomPersianDate(
                    persianDate.getYear() , persianDate.getMonthValue() , persianDate.getDayOfMonth());
            TransActionMenu transActionMenu =convertor.transActionToTransActionMenu(transAction);
            transActionMenu.setPersianDate(customPersianDate);
            transActionMenuList.add(transActionMenu);
        }
        return transActionMenuList;
    }


    public List<TransAction> getSpecificTransActionByTime(Long workerId , LocalDate date) {
        return transActionRepository.findSpecificTransActionByTime(workerId, date);
    }

    public void payTransActionBack(TransActionSave transActionSave , AnnualDetail annualDetail) {

        if (transActionSave.getType().ordinal()==WorkerTransActionType.BACK_LOAN.ordinal()){
            annualDetail.setTotalLoan(annualDetail.getTotalLoan() - transActionSave.getAmount());
        } else if (transActionSave.getType().ordinal()==WorkerTransActionType.BACK_ADVANCE.ordinal()) {
            annualDetail.setTotalAdvance(annualDetail.getTotalAdvance() - transActionSave.getAmount());
        }

    }


}
