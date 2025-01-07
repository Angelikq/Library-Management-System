package Menus;

import Exceptions.BookNotFoundException;
import Exceptions.UserNotFoundException;
import Models.*;
import Services.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class LibrarianMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private LibraryService libraryService;
    private Librarian librarian;


    public void start(LibraryService libraryService) throws UserNotFoundException, BookNotFoundException {
        this.libraryService = libraryService;
        this.librarian =new Librarian( "Admin", libraryService);
        System.out.println("Librarian Menu:");
        if (!authenticateLibrarian()) {
            System.out.println("Authentication failed. Returning to main menu.");
            return;
        }
        while (true) {
            System.out.print("Enter a command (type 'help' for options): ");
            String command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "help" -> printLibrarianHelp();
                case "books" -> librarian.listBooks();
                case "find" ->  librarian.searchBooks();
                case "list" -> librarian.listUsers();
                case "add" -> addBook();
                case "remove" -> removeBook();
                case "history" -> displayReaderHistory();
                case "exit" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Unknown command.");
            }
        }
    }

    private boolean authenticateLibrarian() {
        System.out.print("Enter librarian password: ");
        return scanner.nextLine().equals("admin");
    }

    private void addBook() {
        System.out.println("Enter the title of the book: ");
        scanner.nextLine();
        String title = scanner.nextLine().trim();
        System.out.println("Enter the author of the book: ");
        scanner.nextLine();
        String author = scanner.nextLine().trim();
        System.out.println("Enter the publication year of the book: ");
        scanner.nextLine();
        int year = Integer.parseInt(scanner.nextLine().trim());
        System.out.println("Enter the count of available copies of the book: ");
        scanner.nextLine();
        int countCopies = Integer.parseInt(scanner.nextLine().trim());

        Book newBook = new Book(UUID.randomUUID(), title,author,year, countCopies);
        librarian.addBook(newBook);
        System.out.println("Book added.");
    }

    private void removeBook() throws BookNotFoundException {
        System.out.print("Enter book id to remove: ");
        UUID id = UUID.fromString(scanner.nextLine());
        Book bookToRemove = libraryService.getBook(id);

        if (bookToRemove != null) {
            librarian.removeBook(bookToRemove);
        }
    }

    private static void printLibrarianHelp() {
        System.out.println("Available commands:");
        System.out.println("list - display the list of users");
        System.out.println("add - adding a new book");
        System.out.println("history - display loan history of a reader");
        System.out.println("books - display the list of books");
        System.out.println("find - earch for a book");
        System.out.println("remove - remove a book");
        System.out.println("exit - exit the program");
    }

    private void displayReaderHistory() throws UserNotFoundException {
        System.out.print("Enter reader's card number: ");
        String cardNo = scanner.nextLine().trim();
        Reader reader = librarian.searchUser(cardNo);

        List<Loan> loans = librarian.getLoansForReader(reader);
        if (loans.isEmpty()) {
            System.out.println("This reader has no loan history.");
        }
        else {
            System.out.println("Loan history for " + reader.getName() + " (Card No: " + reader.getCardNo() + "):");
            for (Loan loan : loans) {
                System.out.println("Book: " + loan.getBook().getTitle() + ", Borrowed on: " + loan.getLoanDate() + ", Returned on: " + (loan.getReturnDate() != null ? loan.getReturnDate() : "Not returned yet"));
            }
        }
    }
}
