package Models;

import Services.*;

import java.util.List;

public class Librarian implements User {
    private String name;
    private final BookService bookService;
    private final UserService userService;
    private final LoanService loanService;

    public Librarian(String cardNo, String name, BookService bookService, UserService userService, LoanService loanService) {
        this.name = name;
        this.bookService = bookService;
        this.userService = userService;
        this.loanService = loanService;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRole() {
        return "Librarian";
    }

    public void addBook(Book book) {
        bookService.addBook(book);
    }

    public void removeBook(Book book) {
        bookService.removeBook(book);
    }

    public void listUsers() {
        userService.listUsers();
    }

    @Override
    public List<Book> searchBooks() {
        return bookService.searchBooks();
    }

    public void listBooks(){bookService.listBooks();}

    public List<Reader> searchUser(String cardNo) {
        return userService.searchUser(cardNo);
    }

    public List<Loan> getLoansForReader(Reader reader) {
        return loanService.getLoansForReader(reader);
    }

    public void registerUser(Reader reader){userService.registerUser(reader);};
}
