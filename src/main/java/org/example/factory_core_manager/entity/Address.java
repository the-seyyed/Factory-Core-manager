package org.example.factory_core_manager.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    private Long id ;

    private String city ;

    private String street ;

    private String postalCode ;

    private String alley ;

    private String plateNumber ;

    private String homeNumber ;


}
