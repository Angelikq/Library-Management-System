package Models;


import Services.LibraryService;

public class Reader implements User {
    private String name;
    private String cardNo;

    public Reader(String cardNo, String name) {
        this.cardNo = cardNo;
        this.name = name;
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

    public void borrowBook(Book book, LibraryService libraryService) {
        libraryService.borrowBook(this, book);
    }

    public void returnBook(Book book, LibraryService libraryService) {
        libraryService.returnBook(this, book);
    }
}
