package org.example.factory_core_manager.service;

import lombok.Getter;
import lombok.Setter;
import org.example.factory_core_manager.convertor.Convertor;
import org.example.factory_core_manager.dto.SaveCustomer;
import org.example.factory_core_manager.entity.Customer;
import org.example.factory_core_manager.exception.CustomerExistsException;
import org.example.factory_core_manager.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class CustomerService {

    private CustomerRepository customerRepository;
    private Convertor convertor;

    @Autowired
    public CustomerService(CustomerRepository customerRepository , Convertor convertor) {
        this.customerRepository = customerRepository;
        this.convertor = convertor;
    }

    public void addCustomer(SaveCustomer saveCustomer) {

        if(checkIfCustomerExistByCustomerCode(saveCustomer.getCustomerCode()))
            throw new CustomerExistsException("Customer already exists by customer code : "+saveCustomer.getCustomerCode());
        this.save(convertor.saveCustomerToCustomer(saveCustomer));

    }

    public boolean checkIfCustomerExistByCustomerCode(String customerCode) {
        return this.customerRepository.existsCustomerByCustomerCode(customerCode);
    }

    public boolean checkIfCustomerExistByLastName(String lastName) {
        return this.customerRepository.existsCustomerByLastName((lastName));
    }
    
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

}
