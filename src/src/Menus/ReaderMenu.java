package Menus;

import Models.Reader;       
import Models.Book;
import Services.LibraryService;

import java.util.List;
import java.util.Scanner;   
import java.util.UUID; 

public class ReaderMenu {
    private static final LibraryService libraryService = new LibraryService();
    private static Reader currentReader;
    private static final Scanner scanner = new Scanner(System.in);

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
        scanner.nextLine();
        String cardNo = scanner.nextLine().trim();

        try {
            Integer.parseInt(cardNo);
        } catch (NumberFormatException e) {
            System.out.println("Invalid card number. Please enter a valid number.");
            return;
        }
        List<Reader> foundUsers = libraryService.searchUser(cardNo);

        if (foundUsers.size() == 1) {
            currentReader = foundUsers.get(0);
            System.out.println("Welcome, " + currentReader.getName() + "!");
        } else {
            System.out.println("Card not found. Registering a new user.");
            registerReader();
        }
    }

    private static void registerReader() {
        System.out.print("Enter your name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        String cardNo = libraryService.getNewCardNo();
        currentReader = new Reader(cardNo, name);
        libraryService.registerUser(currentReader);
        System.out.println("Registration complete. Welcome, " + name + "!");
    }

    private static void browseLibrary() {
        System.out.println("You can browse the library without registration.");
        while (true) {
            System.out.print("Enter 'books' to list books, 'find' to search, or 'q' to quit: ");
            switch (scanner.nextLine().trim().toLowerCase()) {
                case "books" -> libraryService.listBooks();
                case "find" -> libraryService.searchBooks();
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
            switch (scanner.nextLine().trim().toLowerCase()) {
                case "help" -> printUserHelp();
                case "list" -> libraryService.listBooks();
                case "find" -> libraryService.searchBooks();
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
        List<Book> foundBooks = libraryService.searchBooks();
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
        if(bookToBorrow.getAvailableCopies() >0) {
            if(currentReader != null){
                libraryService.borrowBook(currentReader, bookToBorrow);
            }
            System.out.printf("You borrowed the book: %s by %s%n", bookToBorrow.getTitle(), bookToBorrow.getAuthor());
        }else{
            System.out.println("Sorry, but we dont currently have available copy of this book.");
            return;
        }
    }


    private boolean promptYesNo (String message){
            System.out.print(message);
            return scanner.next().trim().equalsIgnoreCase("y");
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
