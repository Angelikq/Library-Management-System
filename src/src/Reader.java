import java.util.UUID;

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

    public void borrowBook(Book book, Library library) {
        library.borrowBook(this, book);
    }

    public void returnBook(Book book, Library library) {
        library.returnBook(this, book);
    }
}
