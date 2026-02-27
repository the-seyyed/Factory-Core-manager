package org.example.factory_core_manager.controller;

import org.example.factory_core_manager.dto.GetCustomer;
import org.example.factory_core_manager.dto.SaveCustomer;
import org.example.factory_core_manager.entity.Customer;
import org.example.factory_core_manager.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer-managing")
public class CustomerController {

    private CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/add-customer")
    public void addCustomer(@RequestBody  SaveCustomer saveCustomer) {
        customerService.addCustomer(saveCustomer);
    }

    @GetMapping("/get-customers")
    public List<GetCustomer> getCustomers() {
        return this.customerService.getAllCustomers();
    }

}
