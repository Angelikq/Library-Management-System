package Exceptions;

public class WriteFileException extends Exception {
    public WriteFileException(String filePath) {
        super("Error: Unable to load data from the file " + filePath);
    }
}