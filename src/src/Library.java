import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Library {
    private List<Book> books;
    private List<User> users;
    private List<Loan> loans;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        loans = new ArrayList<>();
    }

    // Dodanie książki do biblioteki
    public void addBook(Book book) {
        books.add(book);
    }

    // Usuwanie książki
    public void removeBook(Book book) {
        books.remove(book);
    }

    // Dodanie użytkownika
    public void registerUser(User user) {
        users.add(user);
    }

    // Wypożyczenie książki
    public void borrowBook(Reader reader, Book book) {
        if (book.getAvailableCopies() > 0) {
            book.decreaseAvailableCopies();
            loans.add(new Loan(reader, book));
            System.out.println(reader.getName() + " has borrowed " + book.getTitle());
        } else {
            System.out.println("No available copies of " + book.getTitle());
        }
    }

    // Zwrócenie książki
    public void returnBook(Reader reader, Book book) {
        for (Loan loan : loans) {
            if (loan.getReader().equals(reader) && loan.getBook().equals(book) && loan.getReturnDate() == null) {
                loan.setReturnDate(LocalDate.now());
                book.increaseAvailableCopies();
                System.out.println(reader.getName() + " has returned " + book.getTitle());
                break;
            }
        }
    }

    // Wyszukiwanie książek
    public List<Book> searchBooks(String titleOrAuthor) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().contains(titleOrAuthor) || book.getAuthor().contains(titleOrAuthor)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    // Wyświetlanie książek
    public void listBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    // Wyświetlanie użytkowników
    public void listUsers() {
        for (User user : users) {
            System.out.println(user.getName() + " - " + user.getRole());
        }
    }

    // Zapis danych do pliku
    public void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("library_data.txt"))) {
            for (Book book : books) {
                writer.write(book.getTitle() + "," + book.getAuthor() + "," + book.getYear() + "," + book.getAvailableCopies() + "\n");
            }
            System.out.println("Data saved to library_data.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Wczytanie danych z pliku
    public void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("library_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String title = parts[0];
                String author = parts[1];
                int year = Integer.parseInt(parts[2]);
                int availableCopies = Integer.parseInt(parts[3]);
                addBook(new Book(title, author, year, availableCopies));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
