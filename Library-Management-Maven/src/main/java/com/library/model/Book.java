package com.library.model;

import java.io.Serializable;

/**
 * Book model class representing a book in the library
 */
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int year;
    private String category;

    public Book() {
    }

    public Book(String isbn, String title, String author, String publisher, int year, String category) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.category = category;
    }

    // Getters and Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format("ISBN: %s | Title: %s | Author: %s | Publisher: %s | Year: %d | Category: %s",
                isbn, title, author, publisher, year, category);
    }

    /**
     * Converts book to CSV format for file storage
     */
    public String toCSV() {
        return String.join(",", isbn, title, author, publisher, String.valueOf(year), category);
    }

    /**
     * Creates a Book object from CSV string
     */
    public static Book fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length == 6) {
            return new Book(parts[0], parts[1], parts[2], parts[3], 
                          Integer.parseInt(parts[4]), parts[5]);
        }
        return null;
    }
}
