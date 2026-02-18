package org.example.factory_core_manager.exception;

public class VacationInTimeIsAlreadyUsed extends RuntimeException {
    public VacationInTimeIsAlreadyUsed(String message) {
        super(message);
    }
}
