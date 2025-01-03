package Services;

import Models.Book;
import Models.Loan;
import Models.Reader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanService {
    private static LoanService instance;
    private List<Loan> loans;
    private FileManager fileManager;
    private BookService bookService;
    private UserService userService;

    public LoanService(List<Loan> loans, FileManager fileManager, BookService bookService, UserService userService) {
        this.loans = loans;
        this.fileManager = fileManager;
        this.bookService = bookService;
        this.userService = userService;
    }

    public void borrowBook(Reader reader, Book book) {
        if (book.getAvailableCopies() > 0) {
            book.decreaseAvailableCopies();
            Loan loan = new Loan(reader, book);
            loans.add(loan);
            fileManager.saveLoans();
            fileManager.saveData();
            System.out.println(reader.getName() + " has borrowed " + book.getTitle());
        } else {
            System.out.println("No available copies of " + book.getTitle());
        }
    }

    public void returnBook(Reader reader, Book book) {
        for (Loan loan : loans) {
            if (loan.getReader().equals(reader) && loan.getBook().equals(book) && loan.getReturnDate() == null) {
                loan.setReturnDate(LocalDate.now());
                book.increaseAvailableCopies();
                fileManager.saveLoans();
                fileManager.saveData();
                System.out.println(reader.getName() + " has returned " + book.getTitle());
                break;
            }
        }
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
