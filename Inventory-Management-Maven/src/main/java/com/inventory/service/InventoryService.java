package com.inventory.service;

import com.inventory.model.Product;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing inventory operations with file storage
 */
public class InventoryService {
    private static final String FILE_PATH = "inventory_products.txt";
    
    /**
     * Add a new product to the inventory
     */
    public boolean addProduct(Product product) {
        try {
            // Check if product already exists
            if (findProductById(product.getProductId()) != null) {
                System.out.println("Error: Product with ID " + product.getProductId() + " already exists!");
                return false;
            }
            
            // Append product to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                writer.write(product.toCSV());
                writer.newLine();
            }
            System.out.println("Product added successfully!");
            return true;
        } catch (IOException e) {
            System.out.println("Error adding product: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * View all products in the inventory
     */
    public List<Product> viewAllProducts() {
        List<Product> products = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            return products;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Product product = Product.fromCSV(line);
                if (product != null) {
                    products.add(product);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading products: " + e.getMessage());
        }
        
        return products;
    }
    
    /**
     * Update a product's details
     */
    public boolean updateProduct(String productId, Product updatedProduct) {
        List<Product> products = viewAllProducts();
        boolean found = false;
        
        // Find and update the product
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(productId)) {
                products.set(i, updatedProduct);
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("Product with ID " + productId + " not found!");
            return false;
        }
        
        // Write all products back to file
        return saveAllProducts(products);
    }
    
    /**
     * Update product price
     */
    public boolean updatePrice(String productId, double newPrice) {
        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Product with ID " + productId + " not found!");
            return false;
        }
        
        product.setPrice(newPrice);
        return updateProduct(productId, product);
    }
    
    /**
     * Update product quantity
     */
    public boolean updateQuantity(String productId, int newQuantity) {
        Product product = findProductById(productId);
        if (product == null) {
            System.out.println("Product with ID " + productId + " not found!");
            return false;
        }
        
        product.setQuantity(newQuantity);
        return updateProduct(productId, product);
    }
    
    /**
     * Remove a product by ID
     */
    public boolean removeProduct(String productId) {
        List<Product> products = viewAllProducts();
        boolean found = false;
        
        // Find and remove the product
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(productId)) {
                products.remove(i);
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("Product with ID " + productId + " not found!");
            return false;
        }
        
        return saveAllProducts(products);
    }
    
    /**
     * Find a product by ID
     */
    public Product findProductById(String productId) {
        List<Product> products = viewAllProducts();
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }
    
    /**
     * Search products by name (partial match)
     */
    public List<Product> searchByName(String name) {
        List<Product> results = new ArrayList<>();
        List<Product> allProducts = viewAllProducts();
        
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(product);
            }
        }
        
        return results;
    }
    
    /**
     * Search products by category
     */
    public List<Product> searchByCategory(String category) {
        List<Product> results = new ArrayList<>();
        List<Product> allProducts = viewAllProducts();
        
        for (Product product : allProducts) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                results.add(product);
            }
        }
        
        return results;
    }
    
    /**
     * Get low stock products (quantity <= threshold)
     */
    public List<Product> getLowStockProducts(int threshold) {
        List<Product> results = new ArrayList<>();
        List<Product> allProducts = viewAllProducts();
        
        for (Product product : allProducts) {
            if (product.getQuantity() <= threshold) {
                results.add(product);
            }
        }
        
        return results;
    }
    
    /**
     * Get total inventory value
     */
    public double getTotalInventoryValue() {
        double total = 0;
        List<Product> products = viewAllProducts();
        
        for (Product product : products) {
            total += product.getTotalValue();
        }
        
        return total;
    }
    
    /**
     * Get total number of products
     */
    public int getTotalProducts() {
        return viewAllProducts().size();
    }
    
    /**
     * Save all products to file
     */
    private boolean saveAllProducts(List<Product> products) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Product product : products) {
                writer.write(product.toCSV());
                writer.newLine();
            }
            System.out.println("Operation completed successfully!");
            return true;
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
            return false;
        }
    }
}
