package org.example.factory_core_manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    private String firstName ;
    private String lastName ;
    //use regex to validation
    @Column(unique=true)
    private String phoneNumber ;

    @Column(unique=true)
    private String customerCode;

}
