package Exceptions;

public abstract class InputException extends Exception {
    public InputException(String var1) {
        super(var1);
    }

    public boolean shouldBreak() {
        return false;
    }
}
