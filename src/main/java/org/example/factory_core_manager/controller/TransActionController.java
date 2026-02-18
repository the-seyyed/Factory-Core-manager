package org.example.factory_core_manager.controller;


import jakarta.servlet.http.HttpSession;
import org.example.factory_core_manager.dto.TransActionMenu;
import org.example.factory_core_manager.dto.TransActionSave;
import org.example.factory_core_manager.entity.AnnualDetail;
import org.example.factory_core_manager.entity.TransAction;
import org.example.factory_core_manager.service.TransActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransActionController {

    private TransActionService transActionService;


    @Autowired
    public TransActionController(TransActionService transActionService) {
        this.transActionService = transActionService;
    }


    @PostMapping("/addNewTransAction/{workerId}")
    public void addNewTransAction(@RequestBody TransActionSave transActionSave ,
                                  @PathVariable Long workerId) {
        transActionService.saveNewTransAction(transActionSave, workerId);
    }

    @GetMapping("/getAllTransactions/{workerId}")
    public List<TransActionMenu> getAllTransactions(@PathVariable Long workerId) {
        return transActionService.getTransActionMenu(workerId);
    }




}
