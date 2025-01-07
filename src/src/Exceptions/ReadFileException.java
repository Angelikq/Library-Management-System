package Exceptions;

public class ReadFileException extends Exception {
    public ReadFileException() {
        super("Error: Unable to load data from the file.");
    }
}