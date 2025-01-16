package Exceptions;

public class InvalidCommandException extends InputException {
    public InvalidCommandException(String command) {
        super("Invalid command: " + command);
    }
}