public class Reader implements User {
    private String name;

    public Reader(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
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
