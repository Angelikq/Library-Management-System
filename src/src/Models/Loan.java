package Models;

import java.time.LocalDate;

public class Loan {
    private Reader reader;
    private Book book;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public Loan(Reader reader, Book book) {
        this.reader = reader;
        this.book = book;
        this.loanDate = LocalDate.now();
    }

    public Reader getReader() {
        return reader;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
