# Address Book Application - Maven Version

## Overview
A console-based Address Book application built using the Model-View-Controller (MVC) architecture pattern in Java with Maven project management.

## Architecture

### Model (com.addressbook.model.Contact)
- Represents individual contacts with properties:
  - ID (auto-generated)
  - Name
  - Phone Number
  - Email Address
  - Physical Address
  - Category (FAMILY, FRIEND, WORK, OTHER)
  - Created Date
  - Last Modified Date
- Business logic methods for contact manipulation

### View (com.addressbook.view.ContactView)
- Console-based user interface
- Displays menus, contact lists, and messages
- Handles user input
- Formatted output with visual separators

### Controller (com.addressbook.controller.ContactController)
- Coordinates between Model and View
- Handles business logic and user actions
- Manages the contact collection
- Implements all application features

## Features

### Core Features
1. ✅ Add new contacts
2. ✅ View all contacts (sorted by name)
3. ✅ Update contact details (name, phone, email, address, category)
4. ✅ Delete contacts (with confirmation)

### Search Functionality
5. ✅ Search by name
6. ✅ Search by phone number
7. ✅ Search by email
8. ✅ Search by category

### Additional Features
9. ✅ View contacts by category
10. ✅ Display statistics (total contacts, emails, addresses, category breakdown)
11. ✅ Duplicate phone number prevention
12. ✅ Contact categorization
13. ✅ Timestamp tracking (creation and modification)

## Prerequisites
- Java Development Kit (JDK) 11 or higher
- Apache Maven 3.6 or higher

## Project Structure
```
AddressBook-Maven/
├── pom.xml                                        # Maven configuration file
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── addressbook/
│                   ├── Main.java                  # Application entry point
│                   ├── model/
│                   │   └── Contact.java           # Model component
│                   ├── view/
│                   │   └── ContactView.java       # View component
│                   └── controller/
│                       └── ContactController.java # Controller component
└── README.md                                      # This file
```

## How to Build and Run

### Option 1: Using Maven Commands

#### Compile the project
```bash
cd AddressBook-Maven
mvn clean compile
```

#### Run the application
```bash
mvn exec:java -Dexec.mainClass="com.addressbook.Main"
```

#### Build executable JAR
```bash
mvn clean package
```

#### Run the JAR file
```bash
java -jar target/addressbook-mvc-1.0.0.jar
```

### Option 2: Using IDE
1. Import the project as a Maven project in your IDE (IntelliJ IDEA, Eclipse, VS Code)
2. Wait for Maven to download dependencies
3. Run the `Main.java` class

## Maven Commands Reference

| Command | Description |
|---------|-------------|
| `mvn clean` | Clean the project (remove target directory) |
| `mvn compile` | Compile the source code |
| `mvn test` | Run unit tests |
| `mvn package` | Create JAR file |
| `mvn install` | Install JAR to local Maven repository |
| `mvn clean package` | Clean and create fresh build |

## Usage Example

1. Run the application using one of the methods above
2. Select option 1 to add a new contact
3. Enter contact details:
   - Name: "John Doe"
   - Phone: "1234567890"
   - Email: "john.doe@example.com"
   - Address: "123 Main St"
   - Category: Family
4. View all contacts using option 2
5. Search for contacts using option 3
6. Update contact details using option 4
7. Delete contacts using option 5
8. View statistics using option 7

## Design Patterns Used
- **MVC (Model-View-Controller)**: Separates concerns into three interconnected components
- **Separation of Concerns**: Each class has a single responsibility
- **Stream API**: Used for filtering and data manipulation
- **Package Organization**: Proper package structure for maintainability

## Validation & Features
- Phone number uniqueness validation
- Empty field validation for required fields
- Confirmation prompt for contact deletion
- Case-insensitive search functionality
- Sorted contact display by name

## Maven Dependencies
- JUnit 4.13.2 (for testing - optional)

## Build Plugins
- Maven Compiler Plugin 3.11.0
- Maven Jar Plugin 3.3.0
- Maven Shade Plugin 3.5.0 (for creating executable JAR)

## Author
Created as part of Advanced Java Programming coursework.

## License
This project is created for educational purposes.
