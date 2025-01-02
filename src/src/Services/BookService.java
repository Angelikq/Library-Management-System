package Services;

import Models.Book;

import Models.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BookService {
    private List<Book> books;
    private FileManager fileManager;

    public BookService(List<Book> books, FileManager fileManager) {
        this.books = books;
        this.fileManager = fileManager;
    }

    public void addBook(Book book) {
        books.add(book);
        fileManager.saveData();
    }

    public void removeBook(Book book) {
        books.remove(book);
        fileManager.saveData();
    }

    public void listBooks() {
        System.out.println("Books in library:");

        System.out.printf("| %-3s | %-30s | %-25s | %-4s | %-3s |%n", "ID", "Title", "Author", "Year", "Stock");
        System.out.println("|-----|--------------------------------|---------------------------|------|-----|");

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);

            System.out.printf("| %-3d | %-30s | %-25s | %-4d | %-3d |%n",
                    i+1,
                    book.getTitle(),
                    book.getAuthor(),
                    book.getYear(),
                    book.getAvailableCopies());
        }
    }

    public List<Book> searchBooks() {
        System.out.print("Enter title, author, or year (min 3 characters): ");
        Scanner scanner = new Scanner(System.in);
        String search = scanner.nextLine().trim();

        if (search.length() < 3) {
            System.out.println("Please enter at least 3 characters.");
            return new ArrayList<>();
        }

        List<Book> foundBooks = books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(search.toLowerCase()) ||
                        book.getAuthor().toLowerCase().contains(search.toLowerCase()) ||
                        String.valueOf(book.getYear()).contains(search))
                .collect(Collectors.toList());

        if (!foundBooks.isEmpty()) {
            System.out.println("Found books:");
            foundBooks.forEach(book -> System.out.printf("ID: %-5s Title: %-30s Author: %-25s Year: %-4d Available Copies: %-3d%n",
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getYear(),
                    book.getAvailableCopies()));
        } else {
            System.out.println("No books found matching the criteria.");
        }
        return foundBooks;
    }
}
