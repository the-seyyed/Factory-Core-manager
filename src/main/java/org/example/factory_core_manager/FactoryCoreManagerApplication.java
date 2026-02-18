package org.example.factory_core_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FactoryCoreManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FactoryCoreManagerApplication.class, args);
    }

}
