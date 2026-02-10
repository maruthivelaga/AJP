# Library Borrowing Management System

A comprehensive Hibernate-based web application for managing library borrowing operations with many-to-many relationships implemented through an association entity.

## Features

### Core CRUD Operations
- **Member Management**: Add, view, edit, and delete library members
- **Book Management**: Manage book catalog with full CRUD operations
- **Borrowing Transactions**: Issue and return books with complete tracking

### Advanced Features
- **Association Entity**: Borrow entity acts as a many-to-many association between Member and Book
- **Member Borrowing History**: View all books borrowed by a specific member
- **Book Borrowing History**: View complete borrowing history for any book
- **Current Borrows Filtering**: Display only currently borrowed books by member
- **Overdue Detection**: Automatic identification of overdue books
- **Overdue List**: Comprehensive view of all overdue borrowings with days overdue
- **Status Tracking**: ISSUED/RETURNED status for each transaction

## Entities

### Member Entity
- **memberId** (Long) - Primary key
- **name** (String) - Member name
- **age** (int) - Member age
- **phone** (String) - Contact number
- **email** (String) - Unique email address
- **borrows** (List<Borrow>) - One-to-many relationship

### Book Entity
- **bookId** (Long) - Primary key
- **title** (String) - Book title
- **author** (String) - Author name
- **category** (String) - Book category
- **isbn** (String) - Unique ISBN
- **borrows** (List<Borrow>) - One-to-many relationship

### Borrow Entity (Association Entity)
- **borrowId** (Long) - Primary key
- **member** (Member) - Many-to-one relationship
- **book** (Book) - Many-to-one relationship
- **issueDate** (LocalDate) - Date of issuance
- **dueDate** (LocalDate) - Return due date
- **status** (BorrowStatus) - ISSUED or RETURNED

## Project Structure

```
Library-Borrowing-Hibernate-Web/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── library/
│       │           ├── entity/
│       │           │   ├── Member.java
│       │           │   ├── Book.java
│       │           │   └── Borrow.java
│       │           ├── dao/
│       │           │   ├── MemberDAO.java
│       │           │   ├── BookDAO.java
│       │           │   └── BorrowDAO.java
│       │           ├── servlet/
│       │           │   ├── MemberServlet.java
│       │           │   ├── BookServlet.java
│       │           │   └── BorrowServlet.java
│       │           └── util/
│       │               └── HibernateUtil.java
│       ├── resources/
│       │   └── hibernate.cfg.xml
│       └── webapp/
│           ├── WEB-INF/
│           │   └── web.xml
│           ├── index.jsp
│           ├── members.jsp
│           ├── member-form.jsp
│           ├── member-borrows.jsp
│           ├── books.jsp
│           ├── book-form.jsp
│           ├── book-history.jsp
│           ├── issue-book.jsp
│           ├── borrows.jsp
│           └── overdue.jsp
├── pom.xml
└── README.md
```

## Technologies Used

- **Java 11**
- **Hibernate 5.6.15** (ORM Framework)
- **JPA Annotations**
- **Servlets & JSP**
- **JSTL** (Tag Library)
- **MySQL 8.0 / H2 Database**
- **Maven** (Build Tool)
- **Apache Tomcat** (Web Server)

## Database Configuration

### H2 Database (Default)
Uses H2 database by default - no setup required.

### MySQL Database
To use MySQL:

```sql
CREATE DATABASE library_borrowing_db;
```

Update `hibernate.cfg.xml`:
```xml
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/library_borrowing_db</property>
<property name="hibernate.connection.username">root</property>
<property name="hibernate.connection.password">your_password</property>
```

## How to Run

```bash
# Compile and package
mvn clean package

# Run with embedded Tomcat
mvn tomcat7:run
```

Access at: `http://localhost:8080/library-borrowing`

## Application Workflows

### 1. Issue a Book
1. Navigate to "Issue Book"
2. Select member and book from dropdowns
3. Set issue date (defaults to today) and due date (defaults to +14 days)
4. Submit to create borrowing record

### 2. Return a Book
1. View "All Transactions" or member's current borrows
2. Click "Return" button for the specific borrow
3. Status updated to RETURNED

### 3. View Member's Borrowing Activity
1. Go to "Members" list
2. Click "View Borrows" for any member
3. See currently borrowed books and complete history

### 4. View Book's Borrowing History
1. Go to "Books" catalog
2. Click "History" for any book
3. View all members who borrowed this book

### 5. Check Overdue Books
1. Click "Overdue List" from home or transactions page
2. View all overdue books with days overdue
3. Contact information displayed for follow-up

## Key Implementation Details

### Association Entity Pattern
The `Borrow` entity implements the association entity pattern for many-to-many relationships:
- Acts as a bridge between Member and Book
- Stores additional metadata (issueDate, dueDate, status)
- Allows tracking borrowing history over time
- Enables multiple borrows of same book by same member at different times

### Overdue Calculation
```java
public boolean isOverdue() {
    return status == BorrowStatus.ISSUED && LocalDate.now().isAfter(dueDate);
}

public long getDaysOverdue() {
    if (isOverdue()) {
        return ChronoUnit.DAYS.between(dueDate, LocalDate.now());
    }
    return 0;
}
```

### Query Examples

**Get overdue borrows:**
```sql
FROM Borrow WHERE status = :status AND dueDate < :today ORDER BY dueDate ASC
```

**Get member's current borrows:**
```sql
FROM Borrow WHERE member.memberId = :memberId AND status = :status
```

**Get book's complete history:**
```sql
FROM Borrow WHERE book.bookId = :bookId ORDER BY issueDate DESC
```

## Sample Data

### Members
```
Name: John Smith | Age: 28 | Email: john@example.com | Phone: 555-0101
Name: Sarah Johnson | Age: 35 | Email: sarah@example.com | Phone: 555-0102
```

### Books
```
Title: The Great Gatsby | Author: F. Scott Fitzgerald | ISBN: 978-0743273565
Title: To Kill a Mockingbird | Author: Harper Lee | ISBN: 978-0061120084
Title: Java Programming | Author: Herbert Schildt | ISBN: 978-1259589331
```

### Borrowing Transactions
```
John Smith borrowed "The Great Gatsby" on 2026-02-01, due 2026-02-15, Status: ISSUED
Sarah Johnson borrowed "Java Programming" on 2026-01-20, due 2026-02-03, Status: ISSUED (Overdue: 7 days)
```

## Features Demonstrated

✅ Association entity for many-to-many relationships  
✅ One-to-many bidirectional relationships  
✅ EAGER and LAZY fetch strategies  
✅ Cascade operations  
✅ Enum types in entities  
✅ LocalDate for date handling  
✅ HQL queries with joins and filters  
✅ Transaction management  
✅ Complete CRUD operations  
✅ Business logic in entity methods  
✅ Responsive web interface  

## Author

Created as part of Advanced Java Programming coursework - Hibernate Web Application with Association Entity Pattern.
