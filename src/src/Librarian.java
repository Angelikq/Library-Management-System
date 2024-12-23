public class Librarian implements User {
    private String name;

    public Librarian(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRole() {
        return "Librarian";
    }

    public void addBook(Book book, Library library) {
        library.addBook(book);
    }

    public void removeBook(Book book, Library library) {
        library.removeBook(book);
    }

    public void listUsers(Library library) {
        library.listUsers();
    }
}
