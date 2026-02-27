package org.example.factory_core_manager.service;

import org.example.factory_core_manager.convertor.Convertor;
import org.example.factory_core_manager.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private Convertor convertor;

    @Autowired
    public CustomerService(CustomerRepository customerRepository , Convertor convertor) {
        this.customerRepository = customerRepository;
        this.convertor = convertor;
    }

}
