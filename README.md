# Library Management System

## Description
The Library Management System allows you to efficiently manage books, users, and loans in a library. Users can be either readers or librarians. The application provides features such as adding new books, registering users, borrowing and returning books, and managing loan history.

## Features

### 1. Book Management:
- Adding new books (title, author, publication year, number of copies).
- Displaying the list of books.
- Searching for books by title or author.
- Deleting books (only available for librarians).

### 2. User Management:
- Registering a user (either reader or librarian).
- Displaying the list of users (only available for librarians).

### 3. Loans:
- Borrowing a book (decreases the number of available copies).
- Returning a book (increases the number of available copies).
- Checking the user's loan history.

## Technologies
- Programming Language: Java
- Data Structures: Lists and objects

## Getting Started

### 1. Running the Application:
To run the application, compile and execute the `Main.java` class. The application will initialize the library and offer the basic features for managing books and users.

### 2. Features:
- **Adding books**: The librarian can add a new book by providing the title, author, publication year, and the number of available copies.
- **Borrowing books**: A reader can borrow a book if there are available copies.
- **Returning books**: A reader can return a book, which will increase the number of available copies.
- **Searching for books**: Books can be searched by title or author.
- **User registration**: New users can be registered as either readers or librarians.

## Installation

1. Clone or download the repository to your local machine.
2. Use an IDE (e.g., IntelliJ IDEA, Eclipse) or compile manually using JDK:
   ```bash
   javac Main.java
   java Main
