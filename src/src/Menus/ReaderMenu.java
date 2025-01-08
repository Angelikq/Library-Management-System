package Menus;
import Exceptions.*;
import Models.*;
import Services.*;
import Utils.InputReader;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ReaderMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static Librarian librarian;
    private static LibraryService libraryService;
    private static Reader currentReader;
    private static InputReader inputReader = new InputReader();

    public void start(LibraryService libraryService) throws LoanException{
        librarian = new Librarian("Admin", libraryService);
        this.libraryService = libraryService;

        try{
            System.out.println("Reader Menu:");
            if (promptYesNo("Are you a registered reader? (y/n): ")) {
                authenticateReader();
            }
            if (currentReader == null) {
                if (promptYesNo("Do you want to register? (y/n): ")) {
                    registerReader();
                } else {
                    browseLibrary();
                }
            }else{
                userActions();
            }
        }catch(InputException e) {
            System.out.println(e.getMessage());
            if (e.shouldBreak()) {
                System.exit(0);
            }
        }catch( NotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    private static void authenticateReader() throws EmptyStringException, ExitCalledException {
        System.out.print("Please enter your card number: ");
        try {
            String cardNo = inputReader.read();
            Integer.parseInt(cardNo);
            currentReader = librarian.searchUser(cardNo);
            System.out.println("Welcome, " + currentReader.getName() + "!");

        } catch (EmptyStringException e) {
            System.out.println(e.getMessage());
            authenticateReader();
        } catch (ExitCalledException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (NumberFormatException e) {
            System.out.println("Invalid card number. Please enter a valid number.");
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Registration process");
            registerReader();
        }
    }

    private static void registerReader() throws EmptyStringException, ExitCalledException {
        System.out.print("Enter your name: ");
        String name = inputReader.read();
        librarian.registerUser(name);
        System.out.println("Registration complete. Welcome, " + name + "!");
    }

    private static void browseLibrary() throws EmptyStringException, ExitCalledException {
        System.out.println("You can browse the library without registration.");
        while (true) {
            System.out.print("Enter 'books' to list books, 'find' to search, or 'q' to quit: ");
            String command = inputReader.read().toLowerCase();
            switch (command) {
                case "books" -> libraryService.listBooks();
                case "find" -> libraryService.searchBooks();
                default -> System.out.println("Invalid command.");
            }
        }
    }

    private static void userActions() throws EmptyStringException, ExitCalledException, NotFoundException, LoanException {
        while (true) {
            System.out.print("Enter a command (type 'help' for options): ");
            String command = inputReader.read().toLowerCase();
            switch (command) {
                case "help" -> printUserHelp();
                case "list" -> libraryService.listBooks();
                case "find" -> libraryService.searchBooks();
                case "borrow" -> borrowBook();
                case "return" -> returnBook();
                default -> System.out.println("Unknown command.");
            }
        }
    }

    private static void borrowBook() throws EmptyStringException, ExitCalledException, NotFoundException, LoanException {

        List<Book> foundBooks = libraryService.searchBooks();
        Book bookToBorrow = null;
        if (foundBooks.size() > 1) {
            System.out.print("Enter the id of the book you want to borrow: ");
            while (bookToBorrow == null) {
                System.out.print("Enter the id of the book you want to borrow or type 'q' to quit: ");
                UUID id = UUID.fromString(inputReader.read());
                bookToBorrow = libraryService.getBook(id);
            }
        } else if (foundBooks.size() == 1) {
            bookToBorrow = foundBooks.get(0);
        }
        libraryService.borrowBook(currentReader, bookToBorrow);


    }

    private boolean promptYesNo (String message){
            System.out.print(message);
            return scanner.nextLine().trim().equalsIgnoreCase("y");
    }

    private static void printUserHelp() {
            System.out.println("list - display the list of available books");
            System.out.println("borrow - borrow a book (provide title or author)");
            System.out.println("find - search for a specific book");
            System.out.println("return - return a borrowed book");
            System.out.println("q - to quit");
    }

    private static void returnBook() throws NotFoundException{
        System.out.println("Your borrowed books:");
        List<Loan> userLoans;

        userLoans = librarian.getLoansForReader(currentReader).stream().filter(loan -> loan.getReturnDate() == null).toList();
        if (userLoans.isEmpty()) {
            System.out.println("You have no borrowed books.");
        } else {
            for (int i = 0; i < userLoans.size(); i++) {
                Loan loan = userLoans.get(i);
                System.out.printf("%d. %s by %s%n", i + 1, loan.getBook().getTitle(), loan.getBook().getAuthor());
            }
            System.out.print("Enter the number of the book to return: ");
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice > 0 && choice <= userLoans.size()) {
                Loan loanToReturn = userLoans.get(choice - 1);
                libraryService.returnBook(currentReader, loanToReturn.getBook());
                System.out.printf("You returned the book: %s%n", loanToReturn.getBook().getTitle());
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}
