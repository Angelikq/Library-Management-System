package Models;

import java.util.UUID;

public class Book {
    private UUID id;
    private String title;
    private String author;
    private int year;
    private int availableCopies;

    public Book(UUID id, String title, String author, int year, int availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.availableCopies = availableCopies;
    }

    public UUID getId() {
        return id;
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
    public void deleteStockOfBook(){
        availableCopies = 0;
    }

    public void increaseAvailableCopies() {
        availableCopies++;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Year: " + year + ", Available Copies: " + availableCopies;
    }
}
