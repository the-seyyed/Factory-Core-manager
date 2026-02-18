package org.example.factory_core_manager.controller;


import org.example.factory_core_manager.dto.PaymentDegreeMenu;
import org.example.factory_core_manager.dto.PaymentDegreeSave;
import org.example.factory_core_manager.entity.PaymentDegree;
import org.example.factory_core_manager.service.PaymentDegreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/PaymentDegreeManaging")
public class PaymentDegreeController {

    private PaymentDegreeService paymentDegreeService;

    @Autowired
    public PaymentDegreeController(PaymentDegreeService paymentDegreeService) {
        this.paymentDegreeService = paymentDegreeService;
    }

    @GetMapping("/getThePaymentDegree")
    public PaymentDegreeMenu getThePaymentDegree() {
        return paymentDegreeService.getPaymentDegreeMenu();
    }

    @PostMapping("/editThePaymentDegree")
    public void editThePaymentDegree(@RequestBody PaymentDegreeSave paymentDegreeSave) {
        this.paymentDegreeService.editPaymentDegree(paymentDegreeSave);
    }

}
