package org.example.factory_core_manager.exception;

public class WorkerHasCheckedOutException extends RuntimeException {
    public WorkerHasCheckedOutException(String message) {
        super(message);
    }
}
