package com.library;

import com.library.model.Book;
import com.library.service.LibraryService;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for Library Management System
 */
public class Main {
    private static LibraryService libraryService = new LibraryService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("  LIBRARY MANAGEMENT SYSTEM");
        System.out.println("===========================================");
        
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    viewAllBooks();
                    break;
                case 3:
                    removeBook();
                    break;
                case 4:
                    searchBooks();
                    break;
                case 5:
                    displayStatistics();
                    break;
                case 6:
                    running = false;
                    System.out.println("\nThank you for using Library Management System!");
                    break;
                default:
                    System.out.println("\nInvalid choice! Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("\n===========================================");
        System.out.println("MENU:");
        System.out.println("1. Add a Book");
        System.out.println("2. View All Books");
        System.out.println("3. Remove a Book");
        System.out.println("4. Search Books");
        System.out.println("5. Display Statistics");
        System.out.println("6. Exit");
        System.out.println("===========================================");
    }
    
    private static void addBook() {
        System.out.println("\n--- ADD A NEW BOOK ---");
        
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();
        
        System.out.print("Enter Publisher: ");
        String publisher = scanner.nextLine();
        
        int year = getIntInput("Enter Year of Publication: ");
        
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        
        Book book = new Book(isbn, title, author, publisher, year, category);
        libraryService.addBook(book);
    }
    
    private static void viewAllBooks() {
        System.out.println("\n--- ALL BOOKS IN LIBRARY ---");
        List<Book> books = libraryService.viewAllBooks();
        
        if (books.isEmpty()) {
            System.out.println("No books found in the library.");
        } else {
            System.out.println("Total Books: " + books.size());
            System.out.println("-------------------------------------------");
            for (int i = 0; i < books.size(); i++) {
                System.out.println((i + 1) + ". " + books.get(i));
            }
        }
    }
    
    private static void removeBook() {
        System.out.println("\n--- REMOVE A BOOK ---");
        System.out.print("Enter ISBN of the book to remove: ");
        String isbn = scanner.nextLine();
        
        libraryService.removeBook(isbn);
    }
    
    private static void searchBooks() {
        System.out.println("\n--- SEARCH BOOKS ---");
        System.out.println("1. Search by Title");
        System.out.println("2. Search by Author");
        System.out.println("3. Search by ISBN");
        
        int choice = getIntInput("Enter your choice: ");
        List<Book> results = null;
        
        switch (choice) {
            case 1:
                System.out.print("Enter title to search: ");
                String title = scanner.nextLine();
                results = libraryService.searchByTitle(title);
                break;
            case 2:
                System.out.print("Enter author to search: ");
                String author = scanner.nextLine();
                results = libraryService.searchByAuthor(author);
                break;
            case 3:
                System.out.print("Enter ISBN to search: ");
                String isbn = scanner.nextLine();
                Book book = libraryService.findBookByISBN(isbn);
                if (book != null) {
                    System.out.println("\nBook Found:");
                    System.out.println(book);
                } else {
                    System.out.println("No book found with ISBN: " + isbn);
                }
                return;
            default:
                System.out.println("Invalid choice!");
                return;
        }
        
        if (results != null && !results.isEmpty()) {
            System.out.println("\nSearch Results: " + results.size() + " book(s) found");
            System.out.println("-------------------------------------------");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i));
            }
        } else {
            System.out.println("No books found matching your search criteria.");
        }
    }
    
    private static void displayStatistics() {
        System.out.println("\n--- LIBRARY STATISTICS ---");
        int totalBooks = libraryService.getTotalBooks();
        System.out.println("Total Books in Library: " + totalBooks);
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }
}
