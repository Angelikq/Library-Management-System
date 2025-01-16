package Exceptions;

public class ReadFileException extends Exception {
    public ReadFileException(String filePath) {
        super("Error: Unable to load data from the file " + filePath);
    }
}