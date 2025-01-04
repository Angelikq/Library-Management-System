package Models;


import Services.*;

import java.util.List;

public class Reader implements User {
    private String name;
    private String cardNo;
    private BookService bookService;
    private LoanService loanService;

    public Reader(String cardNo, String name, BookService bookService, LoanService loanService) {
        this.cardNo = cardNo;
        this.name = name;
        this.bookService = bookService;
        this.loanService = loanService;
    }


    @Override
    public String getName() {
        return name;
    }
    public String getCardNo() {
        return cardNo;
    }

    @Override
    public String getRole() {
        return "Reader";
    }

    public void borrowBook(Book book) {
        loanService.borrowBook(this, book);
    }

    public void returnBook(Book book) {
        loanService.returnBook(this, book);
    }

    @Override
    public void listBooks() {
        bookService.listBooks();
    }

    @Override
    public List<Book> searchBooks() {
        return bookService.searchBooks();
    }

    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
    }
}
