package Exceptions;

public class InvalidBookDataException extends Exception {
    public InvalidBookDataException(String message) {
        super(message);
    }
}