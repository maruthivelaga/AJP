# Address Book Application - Plain Java Version

## Overview
A console-based Address Book application built using the Model-View-Controller (MVC) architecture pattern in Java.

## Architecture

### Model (Contact.java)
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

### View (ContactView.java)
- Console-based user interface
- Displays menus, contact lists, and messages
- Handles user input
- Formatted output with visual separators

### Controller (ContactController.java)
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

## How to Compile and Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher

### Compilation
```bash
javac Contact.java ContactView.java ContactController.java Main.java
```

### Execution
```bash
java Main
```

## Usage Example

1. Run the application
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

## Project Structure
```
AddressBook-Java/
├── Contact.java           # Model component
├── ContactView.java       # View component
├── ContactController.java # Controller component
├── Main.java             # Application entry point
└── README.md             # This file
```

## Design Patterns Used
- **MVC (Model-View-Controller)**: Separates concerns into three interconnected components
- **Separation of Concerns**: Each class has a single responsibility
- **Stream API**: Used for filtering and data manipulation

## Validation & Features
- Phone number uniqueness validation
- Empty field validation for required fields
- Confirmation prompt for contact deletion
- Case-insensitive search functionality
- Sorted contact display by name

## Author
Created as part of Advanced Java Programming coursework.
