# Department Research Publications Management System

## Overview
A comprehensive system to manage, store, search, and analyze faculty & student research publications including journals, conferences, patents, book chapters, and funded projects. Ideal for NAAC, NBA, NIRF, and internal audits.

## Technology Stack
- **Backend**: Java 17, Spring Boot 3.2.1
- **MVC Framework**: Spring MVC
- **ORM**: Spring Data JPA (Hibernate)
- **Security**: Spring Security
- **Frontend**: Thymeleaf, HTML5, CSS3, Bootstrap 5
- **Database**: PostgreSQL
- **Build Tool**: Maven

## Features
- ✅ CRUD operations for publications
- ✅ Search by Year, Author, Journal, Indexing (Scopus/WoS/UGC)
- ✅ Export to Excel and PDF (NAAC format)
- ✅ Dashboard with statistics and charts
- ✅ Role-based access control (Admin, Faculty, Student)
- ✅ Year-wise and Faculty-wise reports

## Prerequisites
- Java 17 or higher
- PostgreSQL 12 or higher
- Maven 3.6+

## Database Setup
1. Install PostgreSQL
2. Create a database:
```sql
CREATE DATABASE research_publications_db;
```
3. Update `src/main/resources/application.properties` with your PostgreSQL credentials

## Installation & Running

1. Clone or navigate to the project directory
2. Update database credentials in `application.properties`
3. Build the project:
```bash
mvn clean install
```
4. Run the application:
```bash
mvn spring-boot:run
```
5. Access the application at: `http://localhost:8080`

## Default Login Credentials
- **Admin**: 
  - Username: `admin@dept.edu`
  - Password: `admin123`
- **Faculty**: 
  - Username: `faculty@dept.edu`
  - Password: `faculty123`

## Project Structure
```
research-publications/
├── src/
│   ├── main/
│   │   ├── java/com/department/research/
│   │   │   ├── ResearchPublicationsApplication.java
│   │   │   ├── model/           # Entity classes
│   │   │   ├── repository/      # Spring Data JPA repositories
│   │   │   ├── service/         # Business logic
│   │   │   ├── controller/      # MVC controllers
│   │   │   ├── config/          # Security & app configuration
│   │   │   └── dto/             # Data Transfer Objects
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── templates/       # Thymeleaf templates
│   │       ├── static/          # CSS, JS, images
│   │       └── data.sql         # Initial data
│   └── test/                    # Unit tests
└── pom.xml
```

## User Roles
- **Admin**: Manage users, approve publications, generate reports
- **Faculty**: Add/Edit publications, view personal stats
- **Student**: View publications (optional)

## API Endpoints
- `/` - Dashboard
- `/login` - Login page
- `/publications` - List all publications
- `/publications/add` - Add new publication
- `/publications/{id}` - View publication details
- `/reports` - Generate reports
- `/admin/users` - User management (Admin only)

## License
MIT License - Free for educational use
