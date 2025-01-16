package Models;

import Exceptions.InputException;
import Exceptions.NotFoundException;
import Exceptions.WriteFileException;
import Services.*;

import java.util.List;

public class Librarian implements User {
    private String name;
    LibraryService libraryService;

    public Librarian( String name, LibraryService libraryService) {
        this.name = name;
        this.libraryService = libraryService;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRole() {
        return "Librarian";
    }

    public void addBook(Book book) throws WriteFileException {
        libraryService.addBook(book);
    }

    public void removeBook(Book book) throws NotFoundException, WriteFileException {
        libraryService.removeBook(book);
    }

    public void listUsers() {
        libraryService.listUsers();
    }

    public List<Book> searchBooks() throws InputException, NotFoundException {
         return libraryService.searchBooks();
    }

    public void listBooks(){libraryService.listBooks();}

    public Reader searchUser(String cardNo) throws NotFoundException {
        return libraryService.searchUser(cardNo);
    }

    public List<Loan> getLoansForReader(Reader reader) {
        return libraryService.getLoansForReader(reader);
    }

    public void registerUser(String name) throws WriteFileException {libraryService.registerUser(name);};
}
