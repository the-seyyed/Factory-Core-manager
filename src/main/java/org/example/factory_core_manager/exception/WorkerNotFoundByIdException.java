package org.example.factory_core_manager.exception;



public class WorkerNotFoundByIdException extends RuntimeException {
    public WorkerNotFoundByIdException(String message) {
        super(message);
    }
}
