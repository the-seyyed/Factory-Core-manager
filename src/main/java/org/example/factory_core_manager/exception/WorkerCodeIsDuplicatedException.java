package org.example.factory_core_manager.exception;

public class WorkerCodeIsDuplicatedException extends RuntimeException {
    public WorkerCodeIsDuplicatedException(String message) {
        super(message);
    }
}
