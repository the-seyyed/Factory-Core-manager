package org.example.factory_core_manager.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor

@NamedEntityGraphs(
        value = {

                @NamedEntityGraph(name = "fetch_with_profile", attributeNodes = {
                        @NamedAttributeNode(value = "profile")
                }),
                @NamedEntityGraph(name = "fetch_with_attendances" , attributeNodes = {
                        @NamedAttributeNode(value = "attendances")
                })
        }
)
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, mappedBy = "worker")
    private Set<Attendance> attendances;

    @OneToOne(mappedBy = "worker" , cascade = CascadeType.REMOVE)
    private AnnualDetail annualDetail;

}
