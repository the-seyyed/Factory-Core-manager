package org.example.factory_core_manager.controller;

import org.example.factory_core_manager.dto.SaveCustomer;
import org.example.factory_core_manager.entity.Customer;
import org.example.factory_core_manager.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer-managing")
public class CustomerController {

    private CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/add-customer")
    public void addCustomer(SaveCustomer saveCustomer) {
        customerService.addCustomer(saveCustomer);
    }

}
