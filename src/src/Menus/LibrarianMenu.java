package Menus;

import java.util.List;
import java.util.Scanner;   // add
import Models.Book;         // add
import Models.Loan;
import Models.Reader;
import Services.LibraryService;

import java.util.UUID;

public class LibrarianMenu {
    private static final LibraryService libraryService = new LibraryService();
    private static final Scanner scanner = new Scanner(System.in);

    public void start() {
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
                case "books" -> libraryService.listBooks();
                case "find" ->  libraryService.searchBooks();
                case "list" -> libraryService.listUsers();
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

    private static void addBook() {
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
        libraryService.addBook(newBook);
        System.out.println("Book added.");
    }

    private void removeBook() {
        System.out.print("Enter book title to remove: ");
        String title = scanner.nextLine();
            //TODO: we cant remove book, due to borrow history of user, we need to set available copies on 0
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

    private void displayReaderHistory() {
        System.out.print("Enter reader's card number: ");
        String cardNo = scanner.nextLine().trim();
        List<Reader> foundReaders = libraryService.searchUser(cardNo);

        if (foundReaders.isEmpty()) {
            System.out.println("No reader found with card number: " + cardNo);
            return;
        }

        Reader reader = foundReaders.get(0);
        List<Loan> loans = libraryService.getLoansForReader(reader);
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
