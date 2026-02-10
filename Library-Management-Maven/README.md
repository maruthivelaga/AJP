# Library Management System

A simple Maven-based Library Management System that allows users to manage books using text file storage.

## Features

- **Add a Book**: Add new books with details (ISBN, title, author, publisher, year, category)
- **View All Books**: Display all books in the library
- **Remove a Book**: Remove books by ISBN
- **Search Books**: Search by title, author, or ISBN
- **Display Statistics**: View total number of books

## Project Structure

```
Library-Management-Maven/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── library/
│                   ├── model/
│                   │   └── Book.java
│                   ├── service/
│                   │   └── LibraryService.java
│                   └── Main.java
├── pom.xml
└── README.md
```

## Technologies Used

- Java 11
- Maven 3.x
- File I/O for data persistence

## How to Run

### Using Maven:

```bash
# Compile the project
mvn clean compile

# Run the application
mvn exec:java
```

### Using Command Line:

```bash
# Compile
mvn clean package

# Run
java -cp target/library-management-1.0.0.jar com.library.Main
```

## Data Storage

All book data is stored in a text file named `library_books.txt` in CSV format:
```
ISBN,Title,Author,Publisher,Year,Category
```

## Sample Usage

1. Run the application
2. Choose option 1 to add a book
3. Enter book details when prompted
4. Choose option 2 to view all books
5. Choose option 3 to remove a book by ISBN
6. Choose option 4 to search for books
7. Choose option 5 to view statistics
8. Choose option 6 to exit

## Example Book Entry

```
ISBN: 978-0134685991
Title: Effective Java
Author: Joshua Bloch
Publisher: Addison-Wesley
Year: 2018
Category: Programming
```

## Author

Created as part of Advanced Java Programming coursework.
