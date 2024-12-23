public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        // Rejestracja użytkowników
        Reader reader1 = new Reader("John Doe");
        Librarian librarian = new Librarian("Alice Smith");

        library.registerUser(reader1);
        library.registerUser(librarian);

        // Dodanie książek do biblioteki
        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, 5);
        Book book2 = new Book("1984", "George Orwell", 1949, 3);
        librarian.addBook(book1, library);
        librarian.addBook(book2, library);

        // Wyświetlanie książek
        System.out.println("Books in library:");
        library.listBooks();

        // Wypożyczenie książki
        reader1.borrowBook(book1, library);

        // Zwrócenie książki
        reader1.returnBook(book1, library);

        // Wyszukiwanie książki
        System.out.println("Search result for '1984':");
        for (Book book : library.searchBooks("1984")) {
            System.out.println(book);
        }

        // Zapisanie danych do pliku
        library.saveData();

        // Wczytanie danych z pliku
        library.loadData();
    }
}
