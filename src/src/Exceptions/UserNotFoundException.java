package Exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User Not Found");
    }
}