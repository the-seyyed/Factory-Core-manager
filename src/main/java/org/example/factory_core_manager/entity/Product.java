package org.example.factory_core_manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private long amount ;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<ProductRecipe> productRecipes;
    public Product() {
        this.productRecipes = new ArrayList<>();
    }


}
