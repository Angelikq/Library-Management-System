package Services;

import Exceptions.*;
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

    public LoanService(List<Loan> loans, FileManager fileManager) {
        this.loans = loans;
        this.fileManager = fileManager;
    }

    public void borrowBook(Reader reader, Book book) throws LoanException, WriteFileException, NotFoundException {
        try {
            if (book.getAvailableCopies() > 0) {
                book.decreaseAvailableCopies();
                Loan loan = new Loan(reader, book);
                loans.add(loan);
                fileManager.saveLoans();
                fileManager.saveData();
                System.out.printf("You borrowed the book: %s by %s%n", book.getTitle(), book.getAuthor());
            } else {
                throw new LoanException(book.getTitle());
            }
        } catch (NullPointerException e) {
            throw new NotFoundException("Book", "provided");
        }
    }

    public void returnBook(Reader reader, Book book) throws NotFoundException, WriteFileException {
        Loan loanToReturn = loans.stream()
                .filter(loan -> loan.getReader().equals(reader) && loan.getBook().equals(book) && loan.getReturnDate() == null)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Loan", "Reader: " + reader.getCardNo() + ", Book: " + book.getId()));

        loanToReturn.setReturnDate(LocalDate.now());
        book.increaseAvailableCopies();
        fileManager.saveLoans();
        fileManager.saveData();
        System.out.println(reader.getName() + " has returned " + book.getTitle());
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
