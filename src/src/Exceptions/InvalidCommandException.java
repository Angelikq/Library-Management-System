package Exceptions;

public class InvalidCommandException extends Exception {
    public InvalidCommandException(String command) {
        super("Invalid command: " + command);
    }
}