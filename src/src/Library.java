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

    // Wyświetlanie użytkowników
    public void listUsers() {
        for (User user : users) {
            System.out.println(user.getName() + " - " + user.getRole());
        }
    }

    // Zapis danych do pliku
    public void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
            for (Book book : books) {
                writer.write(book.getTitle() + "," + book.getAuthor() + "," + book.getYear() + "," + book.getAvailableCopies() + "\n");
            }
            System.out.println("Data saved to books.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Wczytanie danych z pliku
    public void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("id")) continue;

                String[] parts = line.split(",");
                //TODO: wspolna obsluga bledow
                if (parts.length < 5) {
                    System.out.println("Error in line: " + line);
                    continue;
                }

                String title = parts[1].trim();
                String author = parts[2].trim();
                int year = 0;
                int availableCopies = 0;

                try {
                    year = Integer.parseInt(parts[3].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Error year " + parts[3]);
                    continue;
                }


                try {
                    availableCopies = Integer.parseInt(parts[4].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Error copies: " + parts[4]);
                    continue;
                }
                addBook(new Book(title, author, year, availableCopies));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
