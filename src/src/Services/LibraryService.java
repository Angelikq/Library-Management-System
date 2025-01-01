package Services;

import Models.*;
import Models.Reader;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Scanner;


public class LibraryService {
    private List<Book> books;
    private List<Loan> loans;
    private List<Reader> readers;
    private static final Scanner scanner = new Scanner(System.in);

    public LibraryService() {
        books = new ArrayList<>();
        loans = new ArrayList<>();
        readers = new ArrayList<>();
        loadUsersData();
        loadData();
    }

    // Dodanie książki do biblioteki
    public void addBook(Book book) {
        books.add(book);
        saveData();
    }

    // Usuwanie książki
    public void removeBook(Book book) {
        books.remove(book);
    }

    // Dodanie użytkownika
    public void registerUser(Reader reader) {
        readers.add(reader);
        saveUser(reader);
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
    public List<Book> searchBooks() {
        System.out.print("Enter title, author, or year (min 3 characters): ");
        String search = scanner.nextLine().trim();

        List<Book> foundBooks = new ArrayList<>();

        if (search.length() < 3) {
            System.out.println("Please enter at least 3 characters.");
            return foundBooks;
        }
        for (Book book : books) {
            boolean matchesTitle = book.getTitle().toLowerCase().contains(search.toLowerCase());
            boolean matchesAuthor = book.getAuthor().toLowerCase().contains(search.toLowerCase());
            boolean matchesYear = String.valueOf(book.getYear()).contains(search);

            if (matchesTitle || matchesAuthor || matchesYear) {
                foundBooks.add(book);
            }
        }
        if (!foundBooks.isEmpty()) {
            System.out.println("Found books:");
            for (Book book : foundBooks) {
                System.out.printf("ID: %-5s Title: %-30s%n", book.getId(), book.getTitle());
            }
        } else {
            System.out.println("No books found matching the criteria.");
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
        for (User user : readers) {
            System.out.println(user.getName() + " - " + user.getRole());
        }
    }

    // Zapis danych do pliku
    public void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Library-Management-System/src/src/Files/books.txt"))) {
            for (Book book : books) {
                writer.write( book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getYear() + "," + book.getAvailableCopies() + "\n");
            }
            System.out.println("Data saved to books.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(Reader reader) {
        String filePath = "Library-Management-System/src/src/Files/users.txt";
        String newLine = reader.getCardNo() + "," + reader.getName() + ",reader";

        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(newLine + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error while saving user.");
            e.printStackTrace();
        }
    }

    // Wczytanie danych z pliku
    public void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Library-Management-System/src/src/Files/books.txt"))) {
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
                books.add(new Book(UUID.randomUUID(), title, author, year, availableCopies));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUsersData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Library-Management-System/src/src/Files/users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("id")) continue;

                String[] parts = line.split(",");

                String cardNo = parts[0].trim();
                String name = parts[1].trim();

                readers.add(new Reader(cardNo, name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Reader> searchUser(String cardNo) {
        while (cardNo.length() < 6) {
            cardNo = "0" + cardNo;
        }
        List<Reader> foundUser = new ArrayList<>();
        for (Reader reader : readers) {
            if (reader.getCardNo().contains(cardNo) ) {
                foundUser.add(reader);
            }
        }
        return foundUser;
    }
    public String getNewCardNo(){
        int nextNumber = readers.size() + 1;
        return String.format("%06d", nextNumber);
    }

    public List<Loan> getLoansForReader(Reader reader) {
        List<Loan> readerLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getReader().equals(reader)) {
                readerLoans.add(loan);
            }
        }
        return readerLoans;
    }
}
