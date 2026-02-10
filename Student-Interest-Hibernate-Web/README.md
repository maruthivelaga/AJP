# Student Interest Web Application

A Hibernate-based web application for managing students and their interests using many-to-many relationships.

## Features

### Part A - Core Functionality
- **Student Management**: Add, view, edit, and delete students
- **Interest Management**: Add, view, edit, and delete interests
- **Many-to-Many Mapping**: Link multiple interests to students
- **CRUD Operations**: Full Create, Read, Update, Delete functionality

### Part B - Extended Features
- **Multiple Interests per Student**: Each student can enroll in multiple interests
- **Report Feature**: View enrollment statistics grouped by interest category
- **Dynamic Interest Management**: Enroll/remove interests for each student
- **Student Count**: Display number of students enrolled in each interest

## Entities

### Student Entity
- **studentId** (Long) - Primary key, auto-generated
- **name** (String) - Student name
- **age** (int) - Student age
- **email** (String) - Unique email address
- **interests** (Set<Interest>) - Many-to-many relationship

### Interest Entity
- **interestId** (Long) - Primary key, auto-generated
- **title** (String) - Interest title
- **description** (String) - Detailed description
- **category** (String) - Category (Sports, Arts, Technology, etc.)
- **students** (Set<Student>) - Many-to-many relationship

## Project Structure

```
Student-Interest-Hibernate-Web/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── student/
│       │           ├── entity/
│       │           │   ├── Student.java
│       │           │   └── Interest.java
│       │           ├── dao/
│       │           │   ├── StudentDAO.java
│       │           │   └── InterestDAO.java
│       │           ├── servlet/
│       │           │   ├── StudentServlet.java
│       │           │   ├── InterestServlet.java
│       │           │   └── ReportServlet.java
│       │           └── util/
│       │               └── HibernateUtil.java
│       ├── resources/
│       │   └── hibernate.cfg.xml
│       └── webapp/
│           ├── WEB-INF/
│           │   └── web.xml
│           ├── index.jsp
│           ├── students.jsp
│           ├── student-form.jsp
│           ├── manage-interests.jsp
│           ├── interests.jsp
│           ├── interest-form.jsp
│           └── report.jsp
├── pom.xml
└── README.md
```

## Technologies Used

- **Java 11**
- **Hibernate 5.6.15** (ORM Framework)
- **JPA Annotations** (Entity Mapping)
- **Servlets & JSP** (Frontend)
- **JSTL** (JSP Standard Tag Library)
- **MySQL 8.0 / H2 Database**
- **Maven** (Build Tool)
- **Apache Tomcat** (Web Server)

## Database Configuration

### H2 Database (Default)
Uses H2 in-memory database by default. No additional setup required.

### MySQL Database
To use MySQL, create the database and update `hibernate.cfg.xml`:

```sql
CREATE DATABASE student_interest_db;
```

Uncomment MySQL configuration in `hibernate.cfg.xml`:
```xml
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/student_interest_db</property>
<property name="hibernate.connection.username">root</property>
<property name="hibernate.connection.password">your_password</property>
```

## How to Run

### Using Maven and Tomcat:

```bash
# Compile and package
mvn clean package

# Run with embedded Tomcat
mvn tomcat7:run
```

Access the application at: `http://localhost:8080/student-interest`

### Deploy to External Tomcat:

```bash
# Create WAR file
mvn clean package

# Deploy the generated WAR file (target/student-interest-web.war) to Tomcat webapps folder
```

## Application Features

### 1. Student Management
- View all students with their enrolled interests
- Add new students
- Edit student information
- Delete students (removes all interest associations)
- Manage student interests

### 2. Interest Management
- View all interests with student count
- Add new interests with categories
- Edit interest details
- Delete interests (removes all student associations)

### 3. Interest Enrollment
- Link students to multiple interests
- Remove interest enrollments
- Visual distinction between enrolled and available interests
- Real-time enrollment management

### 4. Reports
- Student count by interest category
- Percentage distribution
- Total enrollment statistics

## Sample Data

### Sample Students
```
Name: John Doe | Age: 20 | Email: john@example.com
Name: Jane Smith | Age: 22 | Email: jane@example.com
```

### Sample Interests
```
Title: Football | Category: Sports | Description: Team sport with ball
Title: Guitar Playing | Category: Music | Description: Learn acoustic and electric guitar
Title: Web Development | Category: Technology | Description: HTML, CSS, JavaScript
Title: Oil Painting | Category: Arts | Description: Traditional painting techniques
```

## Relationship Schema

**Many-to-Many Join Table: student_interests**
```
student_id (FK) → students.student_id
interest_id (FK) → interests.interest_id
```

## Key Features Implemented

✅ Many-to-many relationship using JPA annotations  
✅ Cascade operations for data integrity  
✅ EAGER fetching for relationships  
✅ Helper methods for bidirectional association  
✅ Report generation using HQL queries  
✅ Responsive web interface  
✅ Transaction management  
✅ Error handling and validation  

## Author

Created as part of Advanced Java Programming coursework - Hibernate Web Application with Many-to-Many Relationships.
