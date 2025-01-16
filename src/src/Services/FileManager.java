package Services;

import Exceptions.ReadFileException;
import Exceptions.WriteFileException;
import Models.Book;
import Models.Loan;
import Models.Reader;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileManager {
    private List<Book> books;
    private List<Loan> loans;
    private List<Reader> readers;

    public FileManager() throws ReadFileException, WriteFileException {
        this.books = loadData();
        this.readers = loadUsersData();
        this.loans = loadLoans();
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Reader> getReaders() {
        return readers;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void saveData() throws WriteFileException {
        String filePath = "src/src/Files/books.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Book book : books) {
                writer.write( book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getYear() + "," + book.getAvailableCopies() + "\n");
            }
            System.out.println("Data saved to books.txt");
        } catch (IOException e) {
            throw new WriteFileException(filePath);
        }
    }

    public List<Book> loadData() throws ReadFileException {
        List<Book> books = new ArrayList<>();
        String filePath = "src/src/Files/books.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("id")) continue;

                String[] parts = line.split(",");
                if (parts.length < 5) {
                    System.out.println("Error in line: " + line);
                    continue;
                }
                UUID id = UUID.fromString(parts[0].trim());
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
                books.add(new Book(id, title, author, year, availableCopies));
            }
        } catch (IOException e) {
            throw new ReadFileException(filePath);
        }
        return books;

    }

    public void saveUser(Models.Reader reader) throws WriteFileException {
        String filePath = "src/src/Files/users.txt";
        String newLine = reader.getCardNo() + "," + reader.getName() + ",reader";

        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(newLine + System.lineSeparator());
        } catch (IOException e) {
           throw new WriteFileException(filePath);
        }
    }


    public List<Reader> loadUsersData() throws WriteFileException {
        List<Reader> readers = new ArrayList<>();
        String filePath = "src/src/Files/users.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("id")) continue;

                String[] parts = line.split(",");

                String cardNo = parts[0].trim();
                String name = parts[1].trim();

                readers.add(new Reader(cardNo, name));
            }
        } catch (IOException e) {
            throw new WriteFileException(filePath);
        }
        return readers;

    }

    public void saveLoans() throws WriteFileException {
        String filePath = "src/src/Files/loans.txt";
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Loan loan : loans) {
                writer.write(loan.getReader().getCardNo() + "," + loan.getBook().getId() + "," + loan.getLoanDate() + "," + (loan.getReturnDate() != null ? loan.getReturnDate() : "") + "\n");
            }
            System.out.println("Loans saved to loans.txt");
        } catch (IOException e) {
            throw new WriteFileException(filePath);
        }
    }

    public List<Loan> loadLoans() throws WriteFileException {
        List<Loan> loans = new ArrayList<>();
        String filePath = "src/src/Files/loans.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) {
                    System.out.println("Error in line: " + line);
                    continue;
                }

                String cardNo = parts[0].trim();
                UUID bookId = UUID.fromString(parts[1].trim());
                LocalDate loanDate = LocalDate.parse(parts[2].trim());
                LocalDate returnDate = parts.length > 3 && !parts[3].trim().isEmpty() ? LocalDate.parse(parts[3].trim()) : null;

                Reader loanReader = readers.stream().filter(r -> r.getCardNo().equals(cardNo)).findFirst().orElse(null);
                Book loanBook = books.stream().filter(b -> b.getId().equals(bookId)).findFirst().orElse(null);

                if (loanReader != null && loanBook != null) {
                    Loan loan = new Loan(loanReader, loanBook);
                    loan.setLoanDate(loanDate);
                    loan.setReturnDate(returnDate);
                    loans.add(loan);
                }
            }
        } catch (IOException e) {
           throw new WriteFileException(filePath);
        }
        return loans;
    }
}
