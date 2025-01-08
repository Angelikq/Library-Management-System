package Exceptions;

public class NotFoundException extends Exception {
    public NotFoundException(String entityType, String identifier) {
        super(entityType + " not found with identifier: " + identifier);
    }
}