package Services;

import Models.Book;
import Models.Loan;
import Models.Reader;

import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private BookService bookService;
    private UserService userService;
    private LoanService loanService;
    private FileManager fileManager;

    public LibraryService() {
        List<Book> books = new ArrayList<>();
        List<Reader> readers = new ArrayList<>();
        List<Loan> loans = new ArrayList<>();

        fileManager = new FileManager(books, readers, loans);
        bookService = new BookService(books, fileManager);
        userService = new UserService(readers, fileManager);
        loanService = new LoanService(loans, fileManager, bookService, userService);
    }

    public BookService getBookService() {
        return bookService;
    }

    public UserService getUserService() {
        return userService;
    }

    public LoanService getLoanService() {
        return loanService;
    }

    public List<Reader> searchUser(String cardNo) {
        return userService.searchUser(cardNo);
    }

    public String getNewCardNo() {
        return userService.getNewCardNo();
    }

    public void registerUser(Reader reader) {
        userService.registerUser(reader);
    }

    public void borrowBook(Reader reader, Book book) {
        loanService.borrowBook(reader, book);
    }

    public void returnBook(Reader reader, Book book) {
        loanService.returnBook(reader, book);
    }

    public List<Loan> getLoansForReader(Reader reader) {
        return loanService.getLoansForReader(reader);
    }

    public void addBook(Book book) {
        bookService.addBook(book);
    }

    public void removeBook(Book book) {
        bookService.removeBook(book);
    }

    public void listBooks() {
        bookService.listBooks();
    }

    public List<Book> searchBooks() {
        return bookService.searchBooks();
    }

    public void listUsers() {
        userService.listUsers();
    }
}