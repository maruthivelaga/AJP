# Blood Bank Inventory & Issuance System

A console-based CRUD application for managing Blood Bank Inventory using Hibernate with annotation-based entity mapping.

## Features

### Core CRUD Operations
- **Create**: Add new blood units with complete details
- **Read**: View all units or retrieve specific unit by ID
- **Update**: Modify blood unit information (status, expiry date, or all details)
- **Delete**: Remove blood units with validation constraints

### Advanced Features
- **Search Functionality**:
  - Search by blood type (A, B, AB, O)
  - Search by rhesus factor (+/-)
  - Search by blood type and rhesus combination
  - Search by status (AVAILABLE, RESERVED, ISSUED, QUARANTINED)

- **Blood Issuance**: Mark units as ISSUED when distributed

- **Expiry Management**: View all expired blood units

- **Summary Report**: Generate inventory report showing available units grouped by blood type and rhesus factor

## Entity Details

### BloodUnit Entity
- **unitId** (Long) - Primary key, auto-generated
- **bloodType** (Enum) - A, B, AB, O
- **rhesus** (Enum) - POSITIVE (+), NEGATIVE (-)
- **donorId** (String) - Unique donor identifier
- **collectionDate** (LocalDate) - Date of blood collection
- **expiryDate** (LocalDate) - Expiration date
- **status** (Enum) - AVAILABLE, RESERVED, ISSUED, QUARANTINED

## Project Structure

```
BloodBank-Hibernate-Console/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── bloodbank/
│       │           ├── entity/
│       │           │   └── BloodUnit.java
│       │           ├── dao/
│       │           │   └── BloodUnitDAO.java
│       │           ├── util/
│       │           │   └── HibernateUtil.java
│       │           └── Main.java
│       └── resources/
│           └── hibernate.cfg.xml
├── pom.xml
└── README.md
```

## Technologies Used

- Java 11
- Hibernate 5.6.15.Final
- MySQL 8.0 / H2 Database
- JPA Annotations
- Maven

## Database Configuration

### H2 Database (Default - In-Memory)
The application uses H2 database by default for easy testing. No additional setup required.

### MySQL Database
To use MySQL, uncomment the MySQL configuration in `hibernate.cfg.xml` and create the database:

```sql
CREATE DATABASE bloodbank_db;
```

Update connection details in `hibernate.cfg.xml`:
```xml
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/bloodbank_db</property>
<property name="hibernate.connection.username">your_username</property>
<property name="hibernate.connection.password">your_password</property>
```

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
# Package the application
mvn clean package

# Run
java -cp target/bloodbank-hibernate-1.0.0.jar com.bloodbank.Main
```

## Menu Options

1. **Add New Blood Unit** - Register new blood unit in inventory
2. **View All Blood Units** - Display all blood units
3. **View Blood Unit by ID** - Retrieve specific unit details
4. **Update Blood Unit** - Modify unit information
5. **Delete Blood Unit** - Remove unit from inventory
6. **Search Blood Units** - Search by various criteria
7. **Issue Blood Unit** - Mark unit as issued
8. **View Expired Units** - Display expired blood units
9. **Generate Summary Report** - Inventory summary by blood type
10. **Exit** - Close application

## Sample Usage

### Adding a Blood Unit
```
Blood Type: A
Rhesus: POSITIVE (+)
Donor ID: D12345
Collection Date: 2026-02-01
Expiry Date: 2026-03-01
Status: AVAILABLE
```

### Summary Report Example
```
A+: 15 units
A-: 8 units
B+: 12 units
O+: 20 units
O-: 5 units
-------------------------------------------
Total Available Units: 60
```

## Validation & Constraints

- Cannot delete/issue units that are not in appropriate status
- Expired units are automatically identified
- Duplicate blood type combinations are allowed (different donations)
- Proper transaction management with rollback on errors

## Session Management

- SessionFactory managed by HibernateUtil
- Proper session opening and closing
- Transaction management for all write operations
- Automatic schema generation/update

## Error Handling

- Exception handling for all database operations
- Transaction rollback on failures
- User-friendly error messages
- Input validation for dates and enums

## Author

Created as part of Advanced Java Programming coursework - Hibernate Console Application.
