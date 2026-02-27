package org.example.factory_core_manager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer {

    @Id
    private Long id ;

    private String firstName ;
    private String lastName ;
    //use regex to validation
    private String phoneNumber ;

}
