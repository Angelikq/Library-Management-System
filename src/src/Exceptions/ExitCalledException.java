package Exceptions;

public class ExitCalledException extends InputException {
    public ExitCalledException() {
        super(":q character has been provided. Bye!.");
    }

    @Override
    public boolean shouldBreak() {
        return true;
    }
}