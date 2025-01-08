package Services;

import Exceptions.InvalidBookDataException;
import Exceptions.LoanException;
import Exceptions.ReadFileException;
import Exceptions.NotFoundException;
import Models.*;

import Models.Book;

import java.util.List;
import java.util.UUID;

public class LibraryService {
    private FileManager fileManager = new FileManager();
    private BookService bookService;
    private UserService userService;
    private LoanService loanService;
    private List<Book> books;
    private List<Reader> readers;
    private List<Loan> loans;

    public LibraryService() throws ReadFileException {
        this.books = fileManager.getBooks();
        this.readers = fileManager.getReaders();
        this.loans = fileManager.getLoans();
        this.bookService = new BookService(books, fileManager);
        this.userService = new UserService(readers, fileManager);
        this.loanService = new LoanService(loans, fileManager);

    }
    public void addBook(Book book) throws InvalidBookDataException {
        bookService.addBook(book);
    }
    public void removeBook(Book book)throws NotFoundException{bookService.removeBook(book);}
    public Book getBook(UUID bookId) throws NotFoundException {return bookService.getBook(bookId);}
    public void listBooks(){bookService.listBooks();}
    public List<Book> searchBooks(){return bookService.searchBooks();}

    public List<Loan> getLoansForReader(Reader reader){return loanService.getLoansForReader(reader);}
    public void returnBook(Reader reader, Book book)throws NotFoundException{loanService.returnBook(reader,book);}
    public void borrowBook(Reader reader, Book book)throws LoanException {loanService.borrowBook(reader, book);}

    public void listUsers(){userService.listUsers();}
    public Reader searchUser(String cardNo) throws NotFoundException {return userService.searchUser(cardNo);}
    public void registerUser(String name){userService.registerUser(name);}
}