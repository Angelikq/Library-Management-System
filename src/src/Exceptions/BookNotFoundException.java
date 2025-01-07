package Exceptions;

public class BookNotFoundException extends Exception {
    public BookNotFoundException() {
        super("User Not Found");
    }
}