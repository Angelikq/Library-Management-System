public class Book {
    private String title;
    private String author;
    private int year;
    private int availableCopies;

    public Book(String title, String author, int year, int availableCopies) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.availableCopies = availableCopies;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void decreaseAvailableCopies() {
        if (availableCopies > 0) {
            availableCopies--;
        }
    }

    public void increaseAvailableCopies() {
        availableCopies++;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Year: " + year + ", Available Copies: " + availableCopies;
    }
}
