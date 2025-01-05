package Utils;
import Exceptions.*;

import java.util.Scanner;

public class InputReader {

    public String read() throws EmptyStringException, ExitCalledException {
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
}