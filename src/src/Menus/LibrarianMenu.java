package Menus;

import Exceptions.*;
import Models.*;
import Services.*;
import Utils.InputReader;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class LibrarianMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private LibraryService libraryService;
    private Librarian librarian;
    private static InputReader inputReader = new InputReader();

    public void start(LibraryService libraryService) throws WriteFileException, ReadFileException {
        try{
            this.libraryService = libraryService;
            this.librarian =new Librarian( "Admin", libraryService);
            System.out.println("Librarian Menu:");
            authenticateLibrarian();
            librarianActions();
        }catch(AuthenticationFailedException e ){
            System.out.println(e.getMessage());
        }
    }

    private void librarianActions() throws WriteFileException {
        while (true) {
            try{
                System.out.print("Enter a command (type 'help' for options): ");
                String command = inputReader.read();
                switch (command) {
                    case "help" -> printLibrarianHelp();
                    case "books" -> librarian.listBooks();
                    case "find" -> librarian.searchBooks();
                    case "list" -> librarian.listUsers();
                    case "add" -> addBook();
                    case "remove" -> removeBook();
                    case "history" -> displayReaderHistory();
                    default -> throw new InvalidCommandException(command);
            }
        } catch( NotFoundException e){
                System.out.println(e.getMessage());
        }catch(InputException e){
            System.out.println(e.getMessage());
            if(e.shouldBreak())System.exit(0);
        }
        }
}

    private void authenticateLibrarian() throws AuthenticationFailedException {
        System.out.print("Enter librarian password: ");
        boolean isAuthenticationSuccess = scanner.nextLine().equals("admin");
        if(!isAuthenticationSuccess){
            throw new AuthenticationFailedException();
        }
    }

    private void addBook() throws InputException, WriteFileException {
        System.out.println("Enter the title of the book: ");
        String title = inputReader.read();
        System.out.println("Enter the author of the book: ");
        String author = inputReader.read();
        System.out.println("Enter the publication year of the book: ");
        int year = inputReader.readNumber();
        System.out.println("Enter the count of available copies of the book: ");
        int countCopies = inputReader.readNumber();

        Book newBook = new Book(UUID.randomUUID(), title,author,year, countCopies);
        librarian.addBook(newBook);
        System.out.println("Book added.");
    }

    private void removeBook() throws NotFoundException, InputException, WriteFileException {
        System.out.print("Enter book id to remove: ");
        UUID id = UUID.fromString(inputReader.read());
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
        System.out.println("q - to quit");
    }

    private void displayReaderHistory() throws NotFoundException {
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
