import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Library library = new Library();
    static Reader currentReader = null;
    public static void main(String[] args) {


        System.out.print("Welcome to library system!\nIf you are user write 'u' if you are librarian write 'l': ");
        char mode = scanner.next().charAt(0);
        if(mode == 'u') {
            System.out.print("Are you an registered reader? (Write y for 'yes'): ");
            boolean isRegistered = scanner.next().charAt(0) == 'y';
            if(isRegistered) {
                System.out.println("Please, write your cardNo ");
                scanner.nextLine();
                String cardNo = scanner.nextLine();
                List<Reader> foundedUsers = library.searchUser(cardNo);
                if(foundedUsers.size() == 1) {
                    currentReader = foundedUsers.getFirst();
                    System.out.println("Hello, " + currentReader.getName() + "! What we can do for you today?");
                } else if (foundedUsers.size() > 1) {
                    //TODO: Error
                }else{
                    System.out.println("We don't have this card in our system");
                    registerReader();
                }
            }else{
                registerReader();
            }
                while (true) {
                    System.out.print("\nEnter a command: ");
                    String command = scanner.nextLine().trim().toLowerCase();

                    switch (command) {
                        case "help":
                            System.out.println("Available commands:");
                            System.out.println("list - display the list of available books");
                            System.out.println("borrow - borrow a book (provide title or author)");
                            System.out.println("find - search for a specific book");
                            System.out.println("return - return a borrowed book");
                            System.out.println("exit - exit the program");
                            break;

                        case "list":
                            System.out.println("Available books:");
                            library.listBooks();
                            break;

                        case "borrow":
                            System.out.print("Enter the title or author of the book: ");
                            String searchBook = scanner.next();
                            List<Book> foundBooks = library.searchBooks(searchBook);
                            Book bookToBorrow = null;

                            if(foundBooks.size() > 1) {
                                System.out.println("Find more than one book.");
                                for(Book book : foundBooks) {
                                    System.out.println(book);
                                }
                                System.out.print("Enter the id of the book you want to borrow: ");
                                while (bookToBorrow == null){
                                    System.out.print("Enter the id of the book you want to borrow or 'q' to quit: ");
                                    UUID id = UUID.fromString(scanner.next());
                                    bookToBorrow = foundBooks.stream()
                                            .filter(book -> book.getId() == id)
                                            .findFirst()
                                            .orElse(null);
                                }
                            } else if (foundBooks.size() == 1) {
                                bookToBorrow = foundBooks.getFirst();
                            }else{
                                System.out.println("No books found.");
                                return;
                            }
                            if(bookToBorrow.getAvailableCopies() >0) {
                                if(currentReader != null){
                                    library.borrowBook(currentReader, bookToBorrow);
                                }
                                System.out.printf("You borrowed the book: %s by %s%n", bookToBorrow.getTitle(), bookToBorrow.getAuthor());
                            }else{
                                System.out.println("Sorry, but we dont currently have available copy of this book.");
                                return;
                            }
                            break;

                        case "find":
                            System.out.print("Enter the title or author of the book: ");
                            String query = scanner.next().trim();
                            List<Book> foundBook = library.searchBooks(query);
                            if (foundBook.size() > 1) {
                                for(Book book : foundBook) {
                                    System.out.println(book);
                                }
                            } else {
                                System.out.println("The book was not found.");
                            }
                            break;

                        case "return":
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
                            break;

                        case "exit":
                            System.out.println("Thank you for using the library system!");
                            scanner.close();
                            return;

                        default:
                            System.out.println("Unknown command. Type 'help' to see the list of available commands.");
                            break;
                    }

                }


        }else if(mode == 'l') {
            //For what we have a librarian class?
            //TODO: adding books, delete books, list users
            boolean isValidPassword = false;
            System.out.print("Enter your password: ");
            while (!isValidPassword) {
                String password = scanner.nextLine().trim();
                if(password.equals("exit")) System.exit(0);
                isValidPassword = Objects.equals(password, "admin");
                if(isValidPassword) continue;
                System.out.println("Wrong password, try again or type 'exit' to quit: ");
            }

            if(isValidPassword) {
                System.out.print("You are successfully logged in.");
                while(true) {
                    System.out.print("\nEnter a command: ");
                    String command = scanner.nextLine().trim().toLowerCase();

                    switch (command) {
                        case "help":
                            System.out.println("Available commands:");
                            System.out.println("list - display the list of users");
                            System.out.println("add - adding a new book");
                            System.out.println("remove - remove a book");
                            System.out.println("exit - exit the program");
                            break;

                        case "list":
                            System.out.println("List of users:");
                            library.listUsers();
                            break;

                        case "add":
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
                            library.addBook(newBook);


                           break;

                        case "remove":
                            //TODO: we cant remove book, due to borrow history of user, we need to set available copies on 0
                            break;
                }}
            }
        }

    }

    private static void registerReader(){
        System.out.print("Do you want to be registered? (Write y for 'yes'): ");
        boolean wantToRegister = scanner.next().charAt(0) == 'y';
        if(wantToRegister) {
            System.out.println("Please, write your name: ");
            scanner.nextLine();
            String name = scanner.nextLine();
            String cardNo = library.getNewCardNo();
            Reader newReader = new Reader(cardNo, name );
            library.registerUser(newReader);
            currentReader = newReader;
            System.out.println("You are now registered! What we can do for you today?");
        }else{
            noRegistration();
        }
    }
    private static void noRegistration(){
        System.out.print("You are not register user, but if u want u can see what books we have.\n Write 'books' to see our offer or 'q' to quit");
        String answer = scanner.next();
        if(answer.equals("q")) {
            System.out.println("Goodbye!");
            System.exit(0);
        } else if (answer.equals("books")) {
            library.listBooks();
        }
    }

}
