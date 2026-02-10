package com.library.service;

import com.library.model.Book;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing library operations with file storage
 */
public class LibraryService {
    private static final String FILE_PATH = "library_books.txt";
    
    /**
     * Add a new book to the library
     */
    public boolean addBook(Book book) {
        try {
            // Check if book already exists
            if (findBookByISBN(book.getIsbn()) != null) {
                System.out.println("Error: Book with ISBN " + book.getIsbn() + " already exists!");
                return false;
            }
            
            // Append book to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                writer.write(book.toCSV());
                writer.newLine();
            }
            System.out.println("Book added successfully!");
            return true;
        } catch (IOException e) {
            System.out.println("Error adding book: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * View all books in the library
     */
    public List<Book> viewAllBooks() {
        List<Book> books = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            return books;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Book book = Book.fromCSV(line);
                if (book != null) {
                    books.add(book);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading books: " + e.getMessage());
        }
        
        return books;
    }
    
    /**
     * Remove a book by ISBN
     */
    public boolean removeBook(String isbn) {
        List<Book> books = viewAllBooks();
        boolean found = false;
        
        // Find and remove the book
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(isbn)) {
                books.remove(i);
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("Book with ISBN " + isbn + " not found!");
            return false;
        }
        
        // Write remaining books back to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Book book : books) {
                writer.write(book.toCSV());
                writer.newLine();
            }
            System.out.println("Book removed successfully!");
            return true;
        } catch (IOException e) {
            System.out.println("Error removing book: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Find a book by ISBN
     */
    public Book findBookByISBN(String isbn) {
        List<Book> books = viewAllBooks();
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }
    
    /**
     * Search books by title (partial match)
     */
    public List<Book> searchByTitle(String title) {
        List<Book> results = new ArrayList<>();
        List<Book> allBooks = viewAllBooks();
        
        for (Book book : allBooks) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(book);
            }
        }
        
        return results;
    }
    
    /**
     * Search books by author (partial match)
     */
    public List<Book> searchByAuthor(String author) {
        List<Book> results = new ArrayList<>();
        List<Book> allBooks = viewAllBooks();
        
        for (Book book : allBooks) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                results.add(book);
            }
        }
        
        return results;
    }
    
    /**
     * Get total number of books
     */
    public int getTotalBooks() {
        return viewAllBooks().size();
    }
}
