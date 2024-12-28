import java.util.UUID;

public class Reader implements User {
    private String name;
    private UUID id;

    public Reader(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
    public UUID getId() {
        return id;
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
