# Student Interest Creator Web Application

A comprehensive web application using Hibernate that allows users to manage student details and their associated interests.

## 📋 Features

### Part A - Basic CRUD Operations
- **Student Management**
  - Add new students (name, age, email)
  - Update existing student information
  - Delete students
  - View all students
  
- **Interest Management**
  - Add new interests (title, description, category)
  - Update existing interests
  - Delete interests
  - View all interests

### Part B - Advanced Features
- **Multiple Interests per Student**: Each student can be associated with multiple interests (Many-to-Many relationship)
- **Report Feature**: View statistics showing how many students are enrolled under each interest category

## 🏗️ Project Structure

```
assign1/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/studentinterest/
│   │   │       ├── entity/
│   │   │       │   ├── Student.java
│   │   │       │   └── Interest.java
│   │   │       ├── dao/
│   │   │       │   ├── StudentDAO.java
│   │   │       │   └── InterestDAO.java
│   │   │       ├── servlet/
│   │   │       │   ├── StudentServlet.java
│   │   │       │   ├── InterestServlet.java
│   │   │       │   └── ReportServlet.java
│   │   │       └── util/
│   │   │           └── HibernateUtil.java
│   │   ├── resources/
│   │   │   └── hibernate.cfg.xml
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   └── web.xml
│   │       ├── index.jsp
│   │       ├── students.jsp
│   │       ├── student-form.jsp
│   │       ├── interests.jsp
│   │       ├── interest-form.jsp
│   │       └── report.jsp
└── pom.xml
```

## 🗄️ Database Schema

### Student Table
- `student_id` (Primary Key, Auto-increment)
- `name` (VARCHAR, NOT NULL)
- `age` (INT, NOT NULL)
- `email` (VARCHAR, UNIQUE, NOT NULL)

### Interest Table
- `interest_id` (Primary Key, Auto-increment)
- `title` (VARCHAR, NOT NULL)
- `description` (TEXT)
- `category` (VARCHAR, NOT NULL)

### Student_Interests Table (Join Table)
- `student_id` (Foreign Key → Student)
- `interest_id` (Foreign Key → Interest)

## 🚀 Setup Instructions

### Prerequisites
1. **Java Development Kit (JDK)**: Version 11 or higher
2. **Apache Maven**: Version 3.6 or higher
3. **MySQL Database**: Version 8.0 or higher
4. **Apache Tomcat**: Version 9.0 or higher

### Database Setup

1. Install and start MySQL server

2. Create a database (the application will create it automatically if it doesn't exist):
   ```sql
   CREATE DATABASE student_interest_db;
   ```

3. Update database credentials in `src/main/resources/hibernate.cfg.xml`:
   ```xml
   <property name="hibernate.connection.username">root</property>
   <property name="hibernate.connection.password">your_password</property>
   ```

### Application Setup

1. **Clone or navigate to the project directory**:
   ```bash
   cd "c:\Users\maruthi velaga\Documents\AJP\assign1"
   ```

2. **Build the project using Maven**:
   ```bash
   mvn clean package
   ```

3. **Deploy to Tomcat**:
   - Copy the generated WAR file from `target/student-interest-app.war` to Tomcat's `webapps` directory
   - Or use your IDE's Tomcat integration to deploy

4. **Start Tomcat server**

5. **Access the application**:
   ```
   http://localhost:8080/student-interest-app/
   ```

## 📖 How to Use

### Managing Interests
1. Click on "🎯 Manage Interests" from the home page
2. Add new interests by clicking "+ Add New Interest"
3. Fill in the title, description, and category
4. Edit or delete existing interests as needed

### Managing Students
1. Click on "📚 Manage Students" from the home page
2. Add new students by clicking "+ Add New Student"
3. Fill in name, age, and email
4. Select one or multiple interests (Part B feature)
5. Edit or delete existing students as needed

### Viewing Reports (Part B)
1. Click on "📊 View Reports" from the home page
2. View the total number of students
3. See the breakdown of students per interest category

## 🔧 Technologies Used

- **Backend Framework**: Java Servlets
- **ORM**: Hibernate 5.6.15
- **Database**: MySQL 8.0
- **Build Tool**: Maven
- **Frontend**: JSP, HTML5, CSS3
- **Server**: Apache Tomcat 9

## 📊 Database Relationships

- **Many-to-Many Relationship**: Students ↔ Interests
  - Implemented using a join table `student_interests`
  - Allows each student to have multiple interests
  - Allows each interest to be associated with multiple students

## 🎨 Features Highlight

### Part A Implementation
- ✅ Complete CRUD operations for Students
- ✅ Complete CRUD operations for Interests
- ✅ Database entities with proper Hibernate annotations
- ✅ Responsive web interface

### Part B Implementation
- ✅ Many-to-Many relationship (Student can have multiple interests)
- ✅ Report showing student count per interest category
- ✅ Enhanced UI with multiple interest selection
- ✅ Eager fetching for better performance

## 🔍 Sample Data

You can start by adding some sample interests:

**Sports Category:**
- Football - "Team sport played with a ball"
- Basketball - "Indoor team sport"

**Technology Category:**
- Programming - "Software development and coding"
- AI/ML - "Artificial Intelligence and Machine Learning"

**Arts Category:**
- Painting - "Visual art creation"
- Music - "Musical performance and composition"

## 🛠️ Troubleshooting

### Common Issues

1. **Database Connection Error**:
   - Verify MySQL is running
   - Check username and password in hibernate.cfg.xml
   - Ensure database exists

2. **Port Already in Use**:
   - Change Tomcat port in server.xml
   - Or stop the conflicting application

3. **Build Errors**:
   - Run `mvn clean install` to rebuild
   - Check Java and Maven versions

## 📝 Notes

- The application uses `hibernate.hbm2ddl.auto=update` which automatically creates/updates database schema
- Email field is unique - cannot have duplicate emails
- Deleting an interest will remove all student associations with that interest
- The report counts unique students per category

## 👨‍💻 Development

To run in development mode:

1. Import the project into your IDE (Eclipse, IntelliJ IDEA)
2. Configure Tomcat server in IDE
3. Run the application directly from IDE
4. Hot reload is supported for JSP files

## 📄 License

This project is created for educational purposes as part of an Advanced Java Programming assignment.

## 🤝 Contributing

This is an assignment project. However, suggestions for improvements are welcome!

---

**Developed for**: Advanced Java Programming (AJP) Assignment  
**Topic**: Hibernate ORM with Many-to-Many Relationships  
**Version**: 1.0.0
