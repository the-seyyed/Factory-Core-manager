package org.example.factory_core_manager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.factory_core_manager.enumeration.GenderType;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class AllWorkerInfo {


    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String workerCode;

    private int backGroundYears ;

    private int childrenNumber ;

    private GenderType gender;

    private boolean married;

    private int age ;
}
