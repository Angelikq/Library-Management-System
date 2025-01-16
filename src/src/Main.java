import Exceptions.*;
import Menus.*;
import Services.LibraryService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try{
            LibraryService libraryService = new LibraryService();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to the library system!");
            while (true) {
                System.out.print("If you are a user, type 'u'. If you are a librarian, type 'l': ");

                char mode = scanner.next().charAt(0);

                switch (mode) {
                    case 'u' -> new ReaderMenu().start(libraryService);
                    case 'l' -> new LibrarianMenu().start(libraryService);
                    default -> System.out.println("Invalid option.");
                }
            }
        }catch(ReadFileException | WriteFileException e){
            System.out.println(e.getMessage());
        }
    }
}