package org.example.factory_core_manager.entity;


import org.example.factory_core_manager.enumeration.GenderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Profile {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    private LocalDate dateOfBirth;

    private String workerCode;

    //this field must be annotated with annotation that not requeired to be written in the database  ;

    private int backGroundYears ;

    private int childrenNumber ;

    private GenderType gender;

    private boolean hasHome ;

    private boolean married;

    @OneToOne(cascade = {CascadeType.PERSIST , CascadeType.REMOVE})
    private Address address;

    @Transient
    public int getAge(){
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }


}
