package Models;

import java.util.List;

public interface User {
    String getName();
    String getRole();
    void listBooks();
    List<Book> searchBooks();
}
