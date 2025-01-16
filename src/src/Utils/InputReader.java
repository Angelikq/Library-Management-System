package Utils;
import Exceptions.*;

import java.util.Scanner;

public class InputReader {

    public String read() throws InputException {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            throw new EmptyStringException();
        }

        if (input.equals("q")) {
            throw new ExitCalledException();
        }
        return input;
    }
    public int readNumber() throws InputException {
        int number;
        try{
            String input = this.read();
            number = Integer.parseInt(input);
            if (number <= 0) {
                throw new PositiveIntegerException();
            }
        }catch(NumberFormatException e){
            throw new PositiveIntegerException();
        }
        return number;
    }
}