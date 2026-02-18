package org.example.factory_core_manager.exception;

public class WorkerAlreadyCheckedInException extends RuntimeException {
    public WorkerAlreadyCheckedInException(String message) {
        super(message);
    }
}
