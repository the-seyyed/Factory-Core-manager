package org.example.factory_core_manager.exception;

public class DuplicateRequestForCheckOutException extends RuntimeException {
    public DuplicateRequestForCheckOutException(String message) {
        super(message);
    }
}
