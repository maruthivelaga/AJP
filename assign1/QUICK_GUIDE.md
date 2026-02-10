# Student Interest Creator - Quick Reference Guide

## 🎯 Assignment Requirements Checklist

### Part A Requirements ✅
- [x] Student entity (studentId, name, age, email, interestId)
- [x] Interest entity (interestId, title, description, category)
- [x] CRUD operations for Students
- [x] CRUD operations for Interests
- [x] Web application using Hibernate
- [x] Database with two entities
- [x] Link between Student and Interest

### Part B Requirements ✅
- [x] Multiple interests per student (Many-to-Many relationship)
- [x] Report showing student count per interest category
- [x] Enhanced database schema with join table

## 🏃 Quick Start Guide

### 1. Prerequisites Check
```bash
# Check Java version (should be 11+)
java -version

# Check Maven version (should be 3.6+)
mvn -version

# Check MySQL is running
mysql --version
```

### 2. Database Setup
```sql
-- Login to MySQL
mysql -u root -p

-- Create database
CREATE DATABASE student_interest_db;

-- Exit MySQL
exit;
```

### 3. Update Configuration
Edit `src/main/resources/hibernate.cfg.xml`:
- Update MySQL username (default: root)
- Update MySQL password (default: root)
- Update database URL if needed

### 4. Build and Deploy
```bash
# Navigate to project directory
cd "c:\Users\maruthi velaga\Documents\AJP\assign1"

# Clean and build
mvn clean package

# Deploy WAR file to Tomcat webapps folder
# The WAR file will be in: target/student-interest-app.war
```

### 5. Access Application
```
http://localhost:8080/student-interest-app/
```

## 📱 Application Flow

### Adding Data Workflow
1. **Start with Interests** → Add interest categories first
2. **Add Students** → Create student profiles
3. **Link Interests** → Assign multiple interests to students
4. **View Report** → See statistics

### Example Usage Scenario
```
1. Add Interests:
   - Title: "Java Programming"
   - Description: "Object-oriented programming language"
   - Category: "Technology"

2. Add Student:
   - Name: "John Doe"
   - Age: 20
   - Email: "john@example.com"
   - Select Interests: Java Programming, Web Development

3. View Report:
   - See "Technology" category has 1 student
```

## 🔑 Key Features Mapping

| Feature | URL | HTTP Method |
|---------|-----|-------------|
| Home Page | `/` | GET |
| List Students | `/student?action=list` | GET |
| Add Student Form | `/student?action=new` | GET |
| Save Student | `/student?action=save` | POST |
| Edit Student Form | `/student?action=edit&id=X` | GET |
| Delete Student | `/student?action=delete&id=X` | GET |
| List Interests | `/interest?action=list` | GET |
| Add Interest Form | `/interest?action=new` | GET |
| Save Interest | `/interest?action=save` | POST |
| Edit Interest Form | `/interest?action=edit&id=X` | GET |
| Delete Interest | `/interest?action=delete&id=X` | GET |
| View Report | `/report` | GET |

## 🗂️ File Purpose Guide

### Configuration Files
- `pom.xml` - Maven dependencies and build configuration
- `hibernate.cfg.xml` - Database connection and Hibernate settings
- `web.xml` - Servlet mappings and welcome files

### Entity Classes
- `Student.java` - Student entity with many-to-many to Interest
- `Interest.java` - Interest entity with many-to-many to Student

### DAO Classes
- `StudentDAO.java` - Database operations for Student
- `InterestDAO.java` - Database operations for Interest (includes report query)

### Servlets
- `StudentServlet.java` - Handles student-related requests
- `InterestServlet.java` - Handles interest-related requests
- `ReportServlet.java` - Handles report generation (Part B)

### JSP Pages
- `index.jsp` - Landing page with navigation
- `students.jsp` - List all students
- `student-form.jsp` - Add/Edit student (with multiple interest selection)
- `interests.jsp` - List all interests
- `interest-form.jsp` - Add/Edit interest
- `report.jsp` - Display student count report (Part B)

### Utility Classes
- `HibernateUtil.java` - SessionFactory management

## 🎨 UI Features

### Modern Design Elements
- Gradient backgrounds (purple/blue theme)
- Responsive card layouts
- Interactive hover effects
- Clean form designs
- Table-based data display
- Badge-style category tags

### User Experience
- Confirmation dialogs for delete operations
- Multiple checkbox selection for interests
- Clear navigation with Home button on all pages
- Descriptive labels and placeholders
- Visual feedback on hover

## 🔍 Testing Checklist

### Part A Testing
- [ ] Add a new interest
- [ ] Edit an existing interest
- [ ] Delete an interest
- [ ] View all interests
- [ ] Add a new student
- [ ] Edit an existing student
- [ ] Delete a student
- [ ] View all students

### Part B Testing
- [ ] Assign multiple interests to one student
- [ ] Verify many-to-many relationship in database
- [ ] Check report shows correct student counts
- [ ] Verify report groups by category
- [ ] Test that deleting interest updates report

## 🐛 Common Issues & Solutions

### Issue: "ClassNotFoundException: com.mysql.cj.jdbc.Driver"
**Solution**: Ensure MySQL connector dependency is in pom.xml and rebuild

### Issue: "Access denied for user"
**Solution**: Update username/password in hibernate.cfg.xml

### Issue: "Table doesn't exist"
**Solution**: Ensure `hibernate.hbm2ddl.auto` is set to `update` in config

### Issue: "404 Not Found"
**Solution**: Check Tomcat is running and WAR is deployed correctly

### Issue: "Port 8080 already in use"
**Solution**: Stop other Tomcat instances or change port in server.xml

## 📊 Database Verification Queries

```sql
-- Check all tables exist
SHOW TABLES;

-- View student-interest relationships
SELECT s.name, i.title, i.category
FROM students s
JOIN student_interests si ON s.student_id = si.student_id
JOIN interests i ON si.interest_id = i.interest_id;

-- Verify report data
SELECT i.category, COUNT(DISTINCT si.student_id) as count
FROM interests i
LEFT JOIN student_interests si ON i.interest_id = si.interest_id
GROUP BY i.category;
```

## 🎓 Learning Outcomes

This project demonstrates:
1. Hibernate ORM configuration and usage
2. Many-to-Many relationship mapping
3. CRUD operations with Hibernate
4. MVC pattern in web applications
5. Servlet and JSP integration
6. DAO pattern implementation
7. HQL (Hibernate Query Language) for reports
8. Database relationship management

## 📞 Support

For issues or questions:
1. Check README.md for detailed documentation
2. Review hibernate.cfg.xml for configuration
3. Check Tomcat logs for error messages
4. Verify database connection settings

---

**Quick Command Reference:**
```bash
# Build project
mvn clean package

# Run tests
mvn test

# Clean build directory
mvn clean

# Skip tests and build
mvn clean package -DskipTests
```

**Database Commands:**
```sql
-- Reset database
DROP DATABASE student_interest_db;
CREATE DATABASE student_interest_db;

-- View all data
SELECT * FROM students;
SELECT * FROM interests;
SELECT * FROM student_interests;
```
