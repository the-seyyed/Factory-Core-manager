package org.example.factory_core_manager.exception;


public class CustomerExistsException extends RuntimeException {
    public CustomerExistsException(String message) {
        super(message);
    }
}
