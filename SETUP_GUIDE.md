# Setup Instructions for Research Publications Management System

## Prerequisites Installation

### 1. Install Java 17 or Higher
```powershell
# Download from: https://adoptium.net/
# Or use Windows Package Manager:
winget install EclipseAdoptium.Temurin.17.JDK
```

### 2. Install PostgreSQL 12+
```powershell
# Download from: https://www.postgresql.org/download/windows/
# Or use chocolatey:
choco install postgresql
```

### 3. Install Maven (Optional - already included in pom.xml wrapper)
```powershell
# Download from: https://maven.apache.org/download.cgi
# Or use chocolatey:
choco install maven
```

## Database Setup

### Step 1: Create Database
Open PostgreSQL command line (psql) or pgAdmin and run:

```sql
CREATE DATABASE research_publications_db;
```

### Step 2: (Optional) Run Schema Script
If you want to manually create the schema:

```powershell
psql -U postgres -d research_publications_db -f database-schema.sql
```

**Note:** The application will auto-create tables using JPA (spring.jpa.hibernate.ddl-auto=update)

### Step 3: Update Database Credentials
Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/research_publications_db
spring.datasource.username=postgres
spring.datasource.password=YOUR_POSTGRESQL_PASSWORD
```

## Application Setup and Running

### Step 1: Build the Project
```powershell
cd "C:\Users\maruthi velaga\Desktop\AJP"
mvn clean install
```

### Step 2: Run the Application
```powershell
mvn spring-boot:run
```

Or run directly using Java:
```powershell
java -jar target/research-publications-1.0.0.jar
```

### Step 3: Access the Application
Open your browser and navigate to:
```
http://localhost:8080
```

## Default Login Credentials

The system comes with pre-configured demo accounts:

### Admin Account
- **Email:** admin@dept.edu
- **Password:** admin123
- **Permissions:** Full access (manage users, approve publications, generate reports)

### Faculty Account
- **Email:** faculty@dept.edu
- **Password:** faculty123
- **Permissions:** Add/edit publications, manage authors

### Student Account
- **Email:** student@dept.edu
- **Password:** student123
- **Permissions:** View publications only

## Project Structure

```
AJP/
├── src/
│   ├── main/
│   │   ├── java/com/department/research/
│   │   │   ├── ResearchPublicationsApplication.java   # Main application
│   │   │   ├── model/                                # Entity classes
│   │   │   │   ├── User.java
│   │   │   │   ├── Publication.java
│   │   │   │   └── Author.java
│   │   │   ├── repository/                           # Data access layer
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── PublicationRepository.java
│   │   │   │   └── AuthorRepository.java
│   │   │   ├── service/                              # Business logic
│   │   │   │   ├── UserService.java
│   │   │   │   ├── PublicationService.java
│   │   │   │   ├── AuthorService.java
│   │   │   │   └── ReportService.java
│   │   │   ├── controller/                           # MVC controllers
│   │   │   │   ├── HomeController.java
│   │   │   │   ├── PublicationController.java
│   │   │   │   ├── AuthorController.java
│   │   │   │   ├── AdminController.java
│   │   │   │   └── ReportController.java
│   │   │   └── config/                               # Configuration
│   │   │       ├── SecurityConfig.java
│   │   │       └── DataInitializer.java
│   │   └── resources/
│   │       ├── application.properties                # App configuration
│   │       ├── templates/                            # Thymeleaf views
│   │       │   ├── index.html                        # Dashboard
│   │       │   ├── login.html                        # Login page
│   │       │   ├── publications/
│   │       │   │   ├── list.html
│   │       │   │   └── add.html
│   │       │   └── reports/
│   │       │       └── index.html
│   │       └── static/                               # CSS, JS, images
├── pom.xml                                           # Maven dependencies
├── README.md                                         # Project documentation
└── database-schema.sql                               # Database schema

```

## Key Features

### ✅ CRUD Operations
- Create, Read, Update, Delete publications
- Manage authors and users
- Role-based access control

### ✅ Search & Filter
- Search by title, author, year
- Filter by publication type
- Filter by indexing (Scopus, WoS, UGC)

### ✅ Reports & Export
- Export to Excel (.xlsx)
- Export to PDF
- Year-wise reports
- Type-wise reports

### ✅ Dashboard Analytics
- Total publication count
- Year-wise statistics
- Type-wise distribution charts
- Pending approvals

## Troubleshooting

### Issue: Port 8080 already in use
**Solution:** Change port in `application.properties`:
```properties
server.port=8081
```

### Issue: Database connection failed
**Solution:** 
1. Ensure PostgreSQL is running
2. Verify credentials in `application.properties`
3. Check if database exists: `psql -U postgres -l`

### Issue: Maven build fails
**Solution:**
```powershell
mvn clean install -U
```

### Issue: Lombok not working in IDE
**Solution:** Install Lombok plugin for your IDE:
- IntelliJ IDEA: File → Settings → Plugins → Search "Lombok"
- Eclipse: Download lombok.jar and run as Java application

## IDE Setup

### IntelliJ IDEA
1. Open project: File → Open → Select AJP folder
2. Wait for Maven to import dependencies
3. Install Lombok plugin
4. Run: Right-click on ResearchPublicationsApplication.java → Run

### Eclipse
1. Import project: File → Import → Existing Maven Projects
2. Select AJP folder
3. Install Lombok
4. Run: Right-click on project → Run As → Spring Boot App

### VS Code
1. Install Java Extension Pack
2. Install Spring Boot Extension Pack
3. Open folder in VS Code
4. Press F5 to run

## Production Deployment

### 1. Build JAR file
```powershell
mvn clean package -DskipTests
```

### 2. Run in production
```powershell
java -jar target/research-publications-1.0.0.jar --spring.profiles.active=prod
```

### 3. Create application-prod.properties
```properties
server.port=80
spring.datasource.url=jdbc:postgresql://production-db-server:5432/research_db
spring.jpa.hibernate.ddl-auto=validate
spring.thymeleaf.cache=true
```

## Security Recommendations for Production

1. **Change default passwords**
2. **Use environment variables for sensitive data**
3. **Enable HTTPS**
4. **Configure CORS properly**
5. **Set up database backups**

## Support & Documentation

- **Spring Boot Docs:** https://spring.io/projects/spring-boot
- **Thymeleaf Docs:** https://www.thymeleaf.org/
- **PostgreSQL Docs:** https://www.postgresql.org/docs/

## License
MIT License - Free for educational use
