package org.example.factory_core_manager.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PaymentDegree {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    private float backGroundYearsPaymentPerYear;
    private float marriedPayment;
    private float havingAChildPayment;
    private float havingChildrenPayment;

}
