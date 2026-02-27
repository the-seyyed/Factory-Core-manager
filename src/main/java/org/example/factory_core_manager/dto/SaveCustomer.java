package org.example.factory_core_manager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveCustomer {
    private String firstName ;
    private String lastName ;
    //use regex to validation
    private String phoneNumber ;
    private String customerCode;
}
