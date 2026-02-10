package com.inventory.model;

import java.io.Serializable;

/**
 * Product model class representing a product in the inventory
 */
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String productId;
    private String name;
    private String category;
    private int quantity;
    private double price;

    public Product() {
    }

    public Product(String productId, String name, String category, int quantity, double price) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("ID: %-10s | Name: %-20s | Category: %-15s | Quantity: %-5d | Price: $%.2f",
                productId, name, category, quantity, price);
    }

    /**
     * Converts product to CSV format for file storage
     */
    public String toCSV() {
        return String.join(",", productId, name, category, 
                          String.valueOf(quantity), String.valueOf(price));
    }

    /**
     * Creates a Product object from CSV string
     */
    public static Product fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length == 5) {
            return new Product(parts[0], parts[1], parts[2], 
                             Integer.parseInt(parts[3]), Double.parseDouble(parts[4]));
        }
        return null;
    }

    /**
     * Get total value (quantity * price)
     */
    public double getTotalValue() {
        return quantity * price;
    }
}
