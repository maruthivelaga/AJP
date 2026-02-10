# 🚀 Setup and Deployment Checklist

## Pre-Deployment Checklist

### 1. System Requirements ✅

#### Required Software
- [ ] **JDK 11 or higher** installed
  ```bash
  java -version
  # Should show: java version "11.x.x" or higher
  ```

- [ ] **Apache Maven 3.6+** installed
  ```bash
  mvn -version
  # Should show: Apache Maven 3.6.x or higher
  ```

- [ ] **MySQL 8.0+** installed and running
  ```bash
  mysql --version
  # Should show: mysql Ver 8.0.x
  ```

- [ ] **Apache Tomcat 9.0+** installed
  - Download from: https://tomcat.apache.org/download-90.cgi
  - Extract to a folder (e.g., C:\apache-tomcat-9.0.x)

#### Environment Variables (Optional but Recommended)
- [ ] `JAVA_HOME` set to JDK installation directory
- [ ] `MAVEN_HOME` set to Maven installation directory
- [ ] `CATALINA_HOME` set to Tomcat installation directory
- [ ] PATH includes `%JAVA_HOME%\bin` and `%MAVEN_HOME%\bin`

---

## Database Setup ✅

### Step 1: Start MySQL Server
```bash
# On Windows (if installed as service)
net start MySQL80

# Or start from MySQL Workbench or command line
```

### Step 2: Create Database
```bash
# Login to MySQL
mysql -u root -p
# Enter your MySQL root password

# Create database
CREATE DATABASE student_interest_db;

# Verify database created
SHOW DATABASES;

# Exit MySQL
exit;
```

### Step 3: Configure Database Credentials
- [ ] Open `src/main/resources/hibernate.cfg.xml`
- [ ] Update the following properties:
  ```xml
  <property name="hibernate.connection.username">root</property>
  <property name="hibernate.connection.password">YOUR_MYSQL_PASSWORD</property>
  ```
- [ ] Update database URL if needed (default: localhost:3306)

### Step 4: Verify Configuration
- [ ] Database name: `student_interest_db`
- [ ] Username: Correct MySQL username
- [ ] Password: Correct MySQL password
- [ ] Port: 3306 (default) or your custom port
- [ ] Connection URL format: `jdbc:mysql://localhost:3306/student_interest_db`

---

## Application Build ✅

### Step 1: Navigate to Project Directory
```bash
cd "c:\Users\maruthi velaga\Documents\AJP\assign1"
```

### Step 2: Clean Previous Builds
```bash
mvn clean
```
**Expected Output:**
```
[INFO] BUILD SUCCESS
```

### Step 3: Compile and Package
```bash
mvn package
```
**Expected Output:**
```
[INFO] Building war: ...\target\student-interest-app.war
[INFO] BUILD SUCCESS
```

### Step 4: Verify WAR File
- [ ] Check that `target/student-interest-app.war` exists
- [ ] File size should be approximately 10-15 MB

**Troubleshooting Build Issues:**
- If build fails, run: `mvn clean install -X` for detailed logs
- Check internet connection (Maven downloads dependencies)
- Verify pom.xml has no syntax errors

---

## Tomcat Deployment ✅

### Method 1: Manual Deployment (Recommended for First Time)

#### Step 1: Copy WAR File
```bash
# Copy the WAR file to Tomcat's webapps directory
copy "target\student-interest-app.war" "C:\apache-tomcat-9.0.x\webapps\"
```

#### Step 2: Start Tomcat
```bash
# Navigate to Tomcat bin directory
cd C:\apache-tomcat-9.0.x\bin

# Start Tomcat
startup.bat
```

**Expected Behavior:**
- Tomcat console window opens
- WAR file automatically extracts to `webapps/student-interest-app/`
- Application deploys (watch console for errors)

#### Step 3: Verify Deployment
- [ ] Check `webapps/` folder has `student-interest-app` directory
- [ ] Check Tomcat logs: `logs/catalina.out` (no errors)
- [ ] Tomcat console shows: "Deployment of web application archive ... has finished"

### Method 2: IDE Deployment (Eclipse/IntelliJ)

#### Eclipse:
1. Right-click project → Run As → Run on Server
2. Select Tomcat 9.0
3. Click Finish

#### IntelliJ IDEA:
1. Run → Edit Configurations
2. Add New Configuration → Tomcat Server → Local
3. Select Tomcat installation directory
4. Deploy artifact: student-interest-app.war
5. Click Run

---

## Application Access ✅

### Step 1: Access Application
Open browser and navigate to:
```
http://localhost:8080/student-interest-app/
```

### Step 2: Verify Home Page
- [ ] Home page loads successfully
- [ ] Three buttons visible: Manage Students, Manage Interests, View Reports
- [ ] Gradient background displays correctly
- [ ] No 404 or 500 errors

### Step 3: Test Basic Navigation
- [ ] Click "Manage Interests" → Should load interests.jsp
- [ ] Click "Manage Students" → Should load students.jsp  
- [ ] Click "View Reports" → Should load report.jsp
- [ ] Click "Home" button → Returns to index.jsp

---

## Initial Data Setup ✅

### Option 1: Using Web Interface (Recommended)

#### Step 1: Add Sample Interests
1. Navigate to "Manage Interests"
2. Click "+ Add New Interest"
3. Add at least 3 interests:
   - Title: "Java Programming", Category: "Technology"
   - Title: "Football", Category: "Sports"
   - Title: "Painting", Category: "Arts"

#### Step 2: Add Sample Students
1. Navigate to "Manage Students"
2. Click "+ Add New Student"
3. Add at least 2 students:
   - Name: "John Doe", Age: 20, Email: "john@example.com"
   - Select multiple interests (Part B feature)

#### Step 3: Verify Report
1. Navigate to "View Reports"
2. Should see student count per category
3. Total student count should be displayed

### Option 2: Using SQL Script
```bash
# Run the initialization script
mysql -u root -p student_interest_db < database-init.sql
```

---

## Functionality Testing ✅

### Part A: Basic CRUD Testing

#### Test 1: Interest Management
- [ ] Add new interest → Success
- [ ] View all interests → List displays
- [ ] Edit interest → Changes saved
- [ ] Delete interest → Removed from list

#### Test 2: Student Management  
- [ ] Add new student → Success
- [ ] View all students → List displays
- [ ] Edit student → Changes saved
- [ ] Delete student → Removed from list

### Part B: Advanced Features Testing

#### Test 3: Multiple Interests
- [ ] Create student with 3 interests → All saved
- [ ] Edit student, add more interests → Updated correctly
- [ ] Student list shows all interests as tags

#### Test 4: Report Generation
- [ ] Report shows all categories
- [ ] Student count is accurate
- [ ] Total count displays correctly
- [ ] Categories with 0 students show count of 0

---

## Troubleshooting Guide ✅

### Issue 1: Port 8080 Already in Use
**Solution:**
```bash
# Find process using port 8080
netstat -ano | findstr :8080

# Kill the process (replace PID with actual process ID)
taskkill /PID <PID> /F

# Or change Tomcat port in server.xml
```

### Issue 2: Database Connection Error
**Checklist:**
- [ ] MySQL is running
- [ ] Database `student_interest_db` exists
- [ ] Username and password correct in hibernate.cfg.xml
- [ ] MySQL JDBC driver in classpath (check pom.xml)

**Verify:**
```bash
mysql -u root -p
SHOW DATABASES;
# Should see student_interest_db
```

### Issue 3: 404 Not Found
**Checklist:**
- [ ] WAR file deployed to webapps
- [ ] Tomcat is running
- [ ] URL is correct: http://localhost:8080/student-interest-app/
- [ ] No typos in URL

### Issue 4: Build Errors
**Common Fixes:**
```bash
# Clear Maven cache and rebuild
mvn clean install -U

# Skip tests if needed
mvn clean package -DskipTests

# Verbose output for debugging
mvn clean package -X
```

### Issue 5: Hibernate Errors
**Checklist:**
- [ ] hibernate.cfg.xml in src/main/resources
- [ ] Entity classes have proper annotations
- [ ] Database URL is correct
- [ ] MySQL dialect matches MySQL version

---

## Performance Checks ✅

### Step 1: Check Response Times
- [ ] Home page loads < 2 seconds
- [ ] Student list loads < 3 seconds
- [ ] Report generates < 3 seconds

### Step 2: Check Database
```sql
-- Verify tables created
USE student_interest_db;
SHOW TABLES;
-- Should show: students, interests, student_interests

-- Check table structure
DESCRIBE students;
DESCRIBE interests;
DESCRIBE student_interests;
```

### Step 3: Check Logs
- [ ] No errors in Tomcat console
- [ ] No exceptions in logs/catalina.out
- [ ] Hibernate SQL queries logging (if show_sql=true)

---

## Security Checklist ✅

### Before Production Deployment

- [ ] Change default database password
- [ ] Set `hibernate.show_sql` to false in hibernate.cfg.xml
- [ ] Remove or secure database-init.sql
- [ ] Implement user authentication (if required)
- [ ] Enable HTTPS in Tomcat
- [ ] Configure firewall rules
- [ ] Regular database backups

---

## Post-Deployment Checklist ✅

### Immediate Checks (Within 5 minutes)
- [ ] Application accessible from browser
- [ ] All pages load without errors
- [ ] Can add/edit/delete interests
- [ ] Can add/edit/delete students
- [ ] Report displays correctly
- [ ] No console errors in browser

### Short-term Checks (Within 1 hour)
- [ ] Test all CRUD operations
- [ ] Verify data persistence (restart Tomcat, data remains)
- [ ] Test with multiple users/browsers
- [ ] Check for memory leaks (Tomcat console)
- [ ] Verify report accuracy with sample data

### Documentation Review
- [ ] README.md accessible and clear
- [ ] QUICK_GUIDE.md provides helpful tips
- [ ] TESTING_GUIDE.md for validation
- [ ] All URLs in documentation are correct

---

## Maintenance Tasks ✅

### Daily
- [ ] Check Tomcat logs for errors
- [ ] Monitor application performance
- [ ] Verify database connectivity

### Weekly
- [ ] Database backup
- [ ] Review error logs
- [ ] Check disk space

### Monthly
- [ ] Update dependencies (if needed)
- [ ] Security patches
- [ ] Performance optimization

---

## Quick Command Reference

### Maven Commands
```bash
mvn clean                    # Clean build directory
mvn compile                  # Compile source code
mvn package                  # Create WAR file
mvn clean package           # Clean and build
mvn clean install           # Install to local repository
mvn clean package -DskipTests  # Build without tests
```

### Tomcat Commands
```bash
# Windows
startup.bat                  # Start Tomcat
shutdown.bat                 # Stop Tomcat

# Linux/Mac
./startup.sh                # Start Tomcat
./shutdown.sh               # Stop Tomcat
```

### MySQL Commands
```bash
mysql -u root -p            # Login to MySQL
SHOW DATABASES;             # List databases
USE student_interest_db;    # Select database
SHOW TABLES;                # List tables
SELECT * FROM students;     # Query students
```

---

## Deployment Status Template

Use this to track your deployment:

```
[ ] Prerequisites installed (Java, Maven, MySQL, Tomcat)
[ ] Database created and configured
[ ] Application built successfully (mvn package)
[ ] WAR file deployed to Tomcat
[ ] Tomcat started without errors
[ ] Application accessible in browser
[ ] Sample data added
[ ] CRUD operations tested
[ ] Part B features verified (multiple interests, reports)
[ ] No errors in logs
[ ] Documentation reviewed
[ ] Ready for demonstration/submission
```

---

## Support and Resources

### Documentation Files
- `README.md` - Comprehensive guide
- `QUICK_GUIDE.md` - Quick reference
- `PROJECT_STRUCTURE.md` - Architecture overview
- `TESTING_GUIDE.md` - Testing procedures
- `PROJECT_SUMMARY.md` - Project overview

### Configuration Files
- `pom.xml` - Maven dependencies
- `hibernate.cfg.xml` - Database configuration
- `web.xml` - Servlet mappings

### Log Locations
- Tomcat Logs: `<TOMCAT_HOME>/logs/catalina.out`
- Application Logs: Check Tomcat console
- MySQL Logs: Varies by installation

---

## Final Verification

Before marking as complete:

**Functionality:**
- [ ] ✅ All Part A requirements working
- [ ] ✅ All Part B requirements working
- [ ] ✅ No compilation errors
- [ ] ✅ No runtime errors

**Deployment:**
- [ ] ✅ Application deployed successfully
- [ ] ✅ Accessible from browser
- [ ] ✅ Database connected
- [ ] ✅ All pages load

**Quality:**
- [ ] ✅ Professional UI
- [ ] ✅ Responsive design
- [ ] ✅ User-friendly interface
- [ ] ✅ Error handling in place

**Documentation:**
- [ ] ✅ All guides available
- [ ] ✅ Setup instructions clear
- [ ] ✅ Code commented
- [ ] ✅ Testing procedures documented

---

## 🎉 Success Indicators

Your deployment is successful when:

1. ✅ You can access http://localhost:8080/student-interest-app/
2. ✅ All three navigation buttons work
3. ✅ You can add interests and students
4. ✅ Students can have multiple interests
5. ✅ Report shows accurate counts
6. ✅ No errors in browser console
7. ✅ No errors in Tomcat logs
8. ✅ Data persists after Tomcat restart

---

**Deployment Status**: [ ] Complete  
**Date Deployed**: __________  
**Deployed By**: __________  
**Server**: http://localhost:8080/student-interest-app/  

---

_Ready for demonstration and submission!_ 🚀
