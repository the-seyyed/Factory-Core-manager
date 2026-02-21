package org.example.factory_core_manager.exception;

public class InventoryNotPersistedException extends RuntimeException {
    public InventoryNotPersistedException(String message) {
        super(message);
    }
}
