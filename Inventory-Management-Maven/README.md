# Inventory Management System

A Maven-based Inventory Management System with full CRUD operations and text file storage for efficient product management.

## Features

### Core Operations (CRUD)
- **Create**: Add new products with ID, name, category, quantity, and price
- **Read**: View all products or search by various criteria
- **Update**: Modify product details including price and quantity
- **Delete**: Remove products by ID

### Additional Features
- **Search Functionality**: Search by name, category, or ID
- **Low Stock Alert**: View products below a specified quantity threshold
- **Statistics**: Display total products and inventory value
- **Data Persistence**: All data stored in text file format

## Project Structure

```
Inventory-Management-Maven/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── inventory/
│                   ├── model/
│                   │   └── Product.java
│                   ├── service/
│                   │   └── InventoryService.java
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
# Compile and package
mvn clean package

# Run
java -cp target/inventory-management-1.0.0.jar com.inventory.Main
```

## Data Storage

All product data is stored in a text file named `inventory_products.txt` in CSV format:
```
ProductID,Name,Category,Quantity,Price
```

## Menu Options

1. **Add Product** - Add new product to inventory
2. **View All Products** - Display all products in inventory
3. **Update Product** - Update price, quantity, or all details
4. **Remove Product** - Delete a product by ID
5. **Search Products** - Search by name, category, or ID
6. **View Low Stock Products** - Find products below threshold
7. **Display Statistics** - View inventory metrics
8. **Exit** - Close the application

## Sample Usage

### Adding a Product
```
Product ID: P001
Name: Laptop
Category: Electronics
Quantity: 50
Price: 899.99
```

### Updating a Product
- Update price only
- Update quantity only
- Update all details

### Searching Products
- By name (partial match supported)
- By category (exact match)
- By product ID

### Low Stock Alert
Enter a threshold (e.g., 10) to see all products with quantity ≤ threshold.

## Example Product Entry

```
ID: P001       | Name: Laptop              | Category: Electronics   | Quantity: 50    | Price: $899.99
ID: P002       | Name: Mouse               | Category: Accessories   | Quantity: 150   | Price: $19.99
ID: P003       | Name: Keyboard            | Category: Accessories   | Quantity: 5     | Price: $49.99
```

## Statistics

The system provides:
- Total number of products
- Total inventory value (sum of quantity × price for all products)
- Average product value

## Author

Created as part of Advanced Java Programming coursework.
