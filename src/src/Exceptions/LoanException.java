package Exceptions;

public class LoanException extends Exception {
    public LoanException(String bookTitle) {
        super("No available copies of " + bookTitle);
    }
}