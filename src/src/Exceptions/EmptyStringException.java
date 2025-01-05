package Exceptions;

public class EmptyStringException extends InputException {
    public EmptyStringException() {
        super("Empty input has been provided. Please continue.");
    }
}