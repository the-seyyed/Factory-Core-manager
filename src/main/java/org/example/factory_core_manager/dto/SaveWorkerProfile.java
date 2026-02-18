package org.example.factory_core_manager.dto;


import lombok.Getter;
import lombok.Setter;
import org.example.factory_core_manager.enumeration.GenderType;

import java.time.LocalDate;


@Getter
@Setter
public class SaveWorkerProfile {


    private LocalDate dateOfBirth;

    private String workerCode;

    private int age ;

    private int backGroundYears ;

    private int childrenNumber ;

    private GenderType gender;

    private boolean married;
}
