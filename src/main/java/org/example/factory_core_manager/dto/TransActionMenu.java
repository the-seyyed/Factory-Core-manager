package org.example.factory_core_manager.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.factory_core_manager.enumeration.WorkerTransActionType;

import java.time.LocalDate;

@Getter
@Setter
public class TransActionMenu {

    private String description;

    private WorkerTransActionType type;

    private float amount ;

    private CustomPersianDate persianDate ;

}
