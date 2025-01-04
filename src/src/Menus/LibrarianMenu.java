package Menus;

import Models.*;
import Services.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class LibrarianMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Book> books = new ArrayList<>();
    private static final List<Reader> readers = new ArrayList<>();
    private static final List<Loan> loans = new ArrayList<>();
    private static final BookService bookService = new BookService(books, null);
    private static final LoanService loanService = new LoanService(loans, null, bookService);
    private static final FileManager fileManager = new FileManager(books, readers, loans, bookService, loanService);
    private static final UserService userService = new UserService(fileManager.getReaders(), fileManager);
    private static final Librarian librarian = new Librarian("admin", "Admin", bookService, userService, loanService);


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
        librarian.addBook(newBook);
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
        List<Reader> foundReaders = librarian.searchUser(cardNo);

        if (foundReaders.isEmpty()) {
            System.out.println("No reader found with card number: " + cardNo);
            return;
        }

        Reader reader = foundReaders.get(0);
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
