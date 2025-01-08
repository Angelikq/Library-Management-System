package Exceptions;

public class AuthenticationFailedException extends Exception {
    public AuthenticationFailedException() {
        super("Authentication failed. Returning to main menu.");
    }
}