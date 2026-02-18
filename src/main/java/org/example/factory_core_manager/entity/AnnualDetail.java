package org.example.factory_core_manager.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.factory_core_manager.enumeration.WorkerTransActionType;

import java.util.List;

@Entity
@Getter
@Setter
public class AnnualDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    private int totalLeaves ;

    private float totalAdvance;

    private float totalLoan ;

    @OneToMany( mappedBy = "annualDetail" , cascade = CascadeType.REMOVE)
    private List<TransAction> transActions ;

    @OneToOne
    private Worker worker ;
}
