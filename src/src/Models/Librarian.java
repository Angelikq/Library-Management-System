package Models;

import Services.LibraryService;

public class Librarian implements User {
    private String name;

    public Librarian(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRole() {
        return "Librarian";
    }

    public void addBook(Book book, LibraryService libraryService) {
        libraryService.addBook(book);
    }

    public void removeBook(Book book, LibraryService libraryService) {
        libraryService.removeBook(book);
    }

    public void listUsers(LibraryService libraryService) {
        libraryService.listUsers();
    }

    public void searchBooks(LibraryService libraryService){libraryService.searchBooks();}

    public void listBooks(LibraryService libraryService){libraryService.listBooks();}
}
