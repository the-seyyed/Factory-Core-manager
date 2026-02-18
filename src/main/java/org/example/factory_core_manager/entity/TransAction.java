package org.example.factory_core_manager.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.factory_core_manager.enumeration.WorkerTransActionType;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class TransAction {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id ;

    @Enumerated(EnumType.STRING)
    private WorkerTransActionType type;

    private String description;

    private float amount;

    private LocalDate date;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "annual_detail_id")
    private AnnualDetail annualDetail ;



}
