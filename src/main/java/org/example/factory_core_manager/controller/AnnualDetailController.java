package org.example.factory_core_manager.controller;


import org.example.factory_core_manager.dto.AnnualDetailMenu;
import org.example.factory_core_manager.service.AnnualDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/AnnualDetails")
public class AnnualDetailController {

    private AnnualDetailService annualDetailService;

    @Autowired
    public AnnualDetailController(AnnualDetailService annualDetailService) {
        this.annualDetailService = annualDetailService;
    }

    @GetMapping("/getAnnualDetail/{workerId}")
    public AnnualDetailMenu getAnnualDetail(@PathVariable Long workerId) {
        return annualDetailService.getAnnualDetail(workerId);
    }





}
