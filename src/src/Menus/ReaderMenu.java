package Menus;
import Models.*;
import Services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
public class ReaderMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Book> books = new ArrayList<>();
    private static final List<Reader> readers = new ArrayList<>();
    private static final List<Loan> loans = new ArrayList<>();
    private static final FileManager fileManager = new FileManager(books, readers, loans, null, null);
    private static final BookService bookService = new BookService(books, fileManager);
    private static final LoanService loanService = new LoanService(loans, fileManager, bookService);
    private static final UserService userService = new UserService(fileManager.getReaders(), fileManager);
    private static final Librarian librarian = new Librarian("admin", "Admin", bookService, userService, loanService);
    private static Reader currentReader;

    public void start() {
        System.out.println("Reader Menu:");
        if (promptYesNo("Are you a registered reader? (y/n): ")) {
            authenticateReader();
        } else {
            if (promptYesNo("Do you want to register? (y/n): ")) {
                registerReader();
            } else {
                browseLibrary();
                return;
            }
        }
        userActions();
    }

    private static void authenticateReader() {
        System.out.print("Please enter your card number: ");
        String cardNo = scanner.nextLine().trim();

        try {
            Integer.parseInt(cardNo);
        } catch (NumberFormatException e) {
            System.out.println("Invalid card number. Please enter a valid number.");
            return;
        }
        List<Reader> foundUsers = librarian.searchUser(cardNo);

        if (foundUsers.size() == 1) {
            currentReader = foundUsers.get(0);
            currentReader.setLoanService(loanService);
            System.out.println("Welcome, " + currentReader.getName() + "!");
        } else {
            System.out.println("Card not found. Registering a new user.");
            registerReader();
        }
    }

    private static void registerReader() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        String cardNo = userService.getNewCardNo();
        currentReader = new Reader(cardNo, name, bookService, loanService);
        librarian.registerUser(currentReader);
        System.out.println("Registration complete. Welcome, " + name + "!");
    }

    private static void browseLibrary() {
        System.out.println("You can browse the library without registration.");
        while (true) {
            System.out.print("Enter 'books' to list books, 'find' to search, or 'q' to quit: ");
            String command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "books" -> bookService.listBooks();
                case "find" -> bookService.searchBooks();
                case "q" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid command.");
            }
        }
    }

    private static void userActions() {
        while (true) {
            System.out.print("Enter a command (type 'help' for options): ");
            String command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "help" -> printUserHelp();
                case "list" -> bookService.listBooks();
                case "find" -> bookService.searchBooks();
                case "borrow" -> borrowBook();
                case "return" -> returnBook();
                case "exit" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Unknown command.");
            }
        }
    }

    private static void borrowBook() {
        List<Book> foundBooks = bookService.searchBooks();
        Book bookToBorrow = null;

        if (foundBooks.size() > 1) {
            System.out.print("Enter the id of the book you want to borrow: ");
            while (bookToBorrow == null) {
                System.out.print("Enter the id of the book you want to borrow or 'exit' to quit: ");
                UUID id = UUID.fromString(scanner.next());
                bookToBorrow = foundBooks.stream()
                        .filter(book -> book.getId().equals(id))
                        .findFirst()
                        .orElse(null);
            }
        } else if (foundBooks.size() == 1) {
            bookToBorrow = foundBooks.get(0);
        } else {
            System.out.println("No books found.");
            return;
        }
        if (bookToBorrow.getAvailableCopies() > 0) {
            if (currentReader != null) {
                currentReader.borrowBook(bookToBorrow);
            }
            System.out.printf("You borrowed the book: %s by %s%n", bookToBorrow.getTitle(), bookToBorrow.getAuthor());
        } else {
            System.out.println("Sorry, but we don't currently have an available copy of this book.");
        }
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
            System.out.println("exit - exit the program");
    }

    private static void returnBook() {
        System.out.println("Your borrowed books:");
        //TODO: borrow history, loans of user -- need implementation
//                            List<Loan> userLoans = getLoansForReader(currentReader);
//                            if (userLoans.isEmpty()) {
//                                System.out.println("You have no borrowed books.");
//                            } else {
//                                for (int i = 0; i < userLoans.size(); i++) {
//                                    Loan loan = userLoans.get(i);
//                                    System.out.printf("%d. %s by %s%n", i + 1, loan.getBook().getTitle(), loan.getBook().getAuthor());
//                                }
//                                System.out.print("Enter the number of the book to return: ");
//                                int choice = Integer.parseInt(scanner.nextLine().trim());
//                                if (choice > 0 && choice <= userLoans.size()) {
//                                    Loan loanToReturn = userLoans.get(choice - 1);
//                                    currentReader.returnBook(loanToReturn.getBook(), new Library(books, loans));
//                                    System.out.printf("You returned the book: %s%n", loanToReturn.getBook().getTitle());
//                                } else {
//                                    System.out.println("Invalid choice.");
//                                }
//                            }
    }

}
