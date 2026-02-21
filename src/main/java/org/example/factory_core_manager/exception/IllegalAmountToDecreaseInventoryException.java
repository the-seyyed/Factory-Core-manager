package org.example.factory_core_manager.exception;

public class IllegalAmountToDecreaseInventoryException extends RuntimeException {
    public IllegalAmountToDecreaseInventoryException(String message) {
        super(message);
    }
}
