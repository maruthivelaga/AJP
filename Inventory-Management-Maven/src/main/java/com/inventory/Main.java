package com.inventory;

import com.inventory.model.Product;
import com.inventory.service.InventoryService;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for Inventory Management System
 */
public class Main {
    private static InventoryService inventoryService = new InventoryService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("  INVENTORY MANAGEMENT SYSTEM");
        System.out.println("===========================================");
        
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewAllProducts();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    removeProduct();
                    break;
                case 5:
                    searchProducts();
                    break;
                case 6:
                    viewLowStockProducts();
                    break;
                case 7:
                    displayStatistics();
                    break;
                case 8:
                    running = false;
                    System.out.println("\nThank you for using Inventory Management System!");
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
        System.out.println("1. Add Product");
        System.out.println("2. View All Products");
        System.out.println("3. Update Product");
        System.out.println("4. Remove Product");
        System.out.println("5. Search Products");
        System.out.println("6. View Low Stock Products");
        System.out.println("7. Display Statistics");
        System.out.println("8. Exit");
        System.out.println("===========================================");
    }
    
    private static void addProduct() {
        System.out.println("\n--- ADD A NEW PRODUCT ---");
        
        System.out.print("Enter Product ID: ");
        String productId = scanner.nextLine();
        
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        
        int quantity = getIntInput("Enter Quantity: ");
        double price = getDoubleInput("Enter Price: ");
        
        Product product = new Product(productId, name, category, quantity, price);
        inventoryService.addProduct(product);
    }
    
    private static void viewAllProducts() {
        System.out.println("\n--- ALL PRODUCTS IN INVENTORY ---");
        List<Product> products = inventoryService.viewAllProducts();
        
        if (products.isEmpty()) {
            System.out.println("No products found in the inventory.");
        } else {
            System.out.println("Total Products: " + products.size());
            System.out.println("-------------------------------------------");
            for (int i = 0; i < products.size(); i++) {
                System.out.println((i + 1) + ". " + products.get(i));
            }
        }
    }
    
    private static void updateProduct() {
        System.out.println("\n--- UPDATE PRODUCT ---");
        System.out.print("Enter Product ID to update: ");
        String productId = scanner.nextLine();
        
        Product product = inventoryService.findProductById(productId);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }
        
        System.out.println("Current Details: " + product);
        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Update Price");
        System.out.println("2. Update Quantity");
        System.out.println("3. Update All Details");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                double newPrice = getDoubleInput("Enter new price: ");
                inventoryService.updatePrice(productId, newPrice);
                break;
            case 2:
                int newQuantity = getIntInput("Enter new quantity: ");
                inventoryService.updateQuantity(productId, newQuantity);
                break;
            case 3:
                System.out.print("Enter new Product Name (current: " + product.getName() + "): ");
                String name = scanner.nextLine();
                if (name.trim().isEmpty()) name = product.getName();
                
                System.out.print("Enter new Category (current: " + product.getCategory() + "): ");
                String category = scanner.nextLine();
                if (category.trim().isEmpty()) category = product.getCategory();
                
                int quantity = getIntInput("Enter new Quantity (current: " + product.getQuantity() + "): ");
                double price = getDoubleInput("Enter new Price (current: " + product.getPrice() + "): ");
                
                Product updatedProduct = new Product(productId, name, category, quantity, price);
                inventoryService.updateProduct(productId, updatedProduct);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    private static void removeProduct() {
        System.out.println("\n--- REMOVE A PRODUCT ---");
        System.out.print("Enter Product ID to remove: ");
        String productId = scanner.nextLine();
        
        Product product = inventoryService.findProductById(productId);
        if (product != null) {
            System.out.println("Product to be removed: " + product);
            System.out.print("Are you sure you want to remove this product? (yes/no): ");
            String confirm = scanner.nextLine();
            
            if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
                inventoryService.removeProduct(productId);
            } else {
                System.out.println("Removal cancelled.");
            }
        }
    }
    
    private static void searchProducts() {
        System.out.println("\n--- SEARCH PRODUCTS ---");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Category");
        System.out.println("3. Search by ID");
        
        int choice = getIntInput("Enter your choice: ");
        List<Product> results = null;
        
        switch (choice) {
            case 1:
                System.out.print("Enter product name to search: ");
                String name = scanner.nextLine();
                results = inventoryService.searchByName(name);
                break;
            case 2:
                System.out.print("Enter category to search: ");
                String category = scanner.nextLine();
                results = inventoryService.searchByCategory(category);
                break;
            case 3:
                System.out.print("Enter Product ID to search: ");
                String productId = scanner.nextLine();
                Product product = inventoryService.findProductById(productId);
                if (product != null) {
                    System.out.println("\nProduct Found:");
                    System.out.println(product);
                } else {
                    System.out.println("No product found with ID: " + productId);
                }
                return;
            default:
                System.out.println("Invalid choice!");
                return;
        }
        
        if (results != null && !results.isEmpty()) {
            System.out.println("\nSearch Results: " + results.size() + " product(s) found");
            System.out.println("-------------------------------------------");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i));
            }
        } else {
            System.out.println("No products found matching your search criteria.");
        }
    }
    
    private static void viewLowStockProducts() {
        System.out.println("\n--- LOW STOCK PRODUCTS ---");
        int threshold = getIntInput("Enter threshold quantity: ");
        
        List<Product> lowStockProducts = inventoryService.getLowStockProducts(threshold);
        
        if (lowStockProducts.isEmpty()) {
            System.out.println("No low stock products found.");
        } else {
            System.out.println("Products with quantity <= " + threshold + ":");
            System.out.println("-------------------------------------------");
            for (int i = 0; i < lowStockProducts.size(); i++) {
                System.out.println((i + 1) + ". " + lowStockProducts.get(i));
            }
        }
    }
    
    private static void displayStatistics() {
        System.out.println("\n--- INVENTORY STATISTICS ---");
        int totalProducts = inventoryService.getTotalProducts();
        double totalValue = inventoryService.getTotalInventoryValue();
        
        System.out.println("Total Products: " + totalProducts);
        System.out.println("Total Inventory Value: $" + String.format("%.2f", totalValue));
        
        if (totalProducts > 0) {
            System.out.println("Average Product Value: $" + String.format("%.2f", totalValue / totalProducts));
        }
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
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }
}
