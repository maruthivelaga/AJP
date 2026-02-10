# Patient-Doctor Appointment System

A web application built with Hibernate and Java Servlets for managing patient-doctor appointments.

## Features

- **Patient Management**: Add, update, delete, and view patient records
- **Doctor Management**: Add, update, delete, and view doctor records
- **Appointment Booking**: Link patients to doctors (many-to-many relationship)
- **Appointment Cancellation**: Remove patient-doctor associations

## Technology Stack

- **Backend**: Java, Hibernate 5.6.15
- **Database**: MySQL 8.0
- **Web**: Java Servlets, JSP, JSTL
- **Build Tool**: Maven
- **Server**: Apache Tomcat 9.0+

## Database Schema

### Patient Table
- `patient_id` (Primary Key, Auto-generated)
- `name`
- `age`
- `phone`
- `email`

### Doctor Table
- `doctor_id` (Primary Key, Auto-generated)
- `name`
- `specialization`
- `email`

### Appointments Table (Join Table)
- `patient_id` (Foreign Key)
- `doctor_id` (Foreign Key)

## Setup Instructions

### Prerequisites
1. JDK 11 or higher
2. Apache Tomcat 9.0 or higher
3. MySQL Server 8.0
4. Maven

### Database Configuration

1. Create a MySQL database (or let Hibernate create it automatically):
```sql
CREATE DATABASE appointment_db;
```

2. Update the database credentials in `src/main/resources/hibernate.cfg.xml`:
```xml
<property name="hibernate.connection.username">root</property>
<property name="hibernate.connection.password">root</property>
```

### Build and Deploy

1. Build the project:
```bash
mvn clean package
```

2. Deploy the generated WAR file (`target/patient-doctor-appointment-1.0-SNAPSHOT.war`) to Tomcat's `webapps` directory

3. Start Tomcat server

4. Access the application at: `http://localhost:8080/patient-doctor-appointment-1.0-SNAPSHOT/`

## Application Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/appointment/
│   │       ├── model/          # Entity classes
│   │       │   ├── Patient.java
│   │       │   └── Doctor.java
│   │       ├── dao/            # Data Access Objects
│   │       │   ├── PatientDAO.java
│   │       │   └── DoctorDAO.java
│   │       ├── servlet/        # Servlet controllers
│   │       │   ├── PatientServlet.java
│   │       │   ├── DoctorServlet.java
│   │       │   └── AppointmentServlet.java
│   │       └── util/           # Utility classes
│   │           └── HibernateUtil.java
│   ├── resources/
│   │   └── hibernate.cfg.xml   # Hibernate configuration
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── views/          # JSP views
│       │   └── web.xml         # Web application configuration
│       └── index.html          # Landing page
```

## Usage

### Managing Patients
- Navigate to "Manage Patients"
- Click "Add New Patient" to create a patient record
- Use "Edit" to modify existing patient information
- Use "Delete" to remove a patient (this will also cancel all their appointments)

### Managing Doctors
- Navigate to "Manage Doctors"
- Click "Add New Doctor" to create a doctor record
- Use "Edit" to modify existing doctor information
- Use "Delete" to remove a doctor (this will also cancel all their appointments)

### Managing Appointments
- Navigate to "Manage Appointments"
- Click "Book New Appointment"
- Select a patient and a doctor from the dropdowns
- Click "Book Appointment" to create the association
- Use "Cancel" to remove an appointment

## Key Features of the Implementation

1. **Many-to-Many Mapping**: Implemented using JPA annotations with a join table
2. **Bidirectional Relationship**: Both Patient and Doctor entities maintain references to each other
3. **Cascade Operations**: Configured to persist and merge related entities
4. **CRUD Operations**: Complete Create, Read, Update, Delete functionality for both entities
5. **Appointment Management**: Separate servlet for managing the many-to-many relationship
6. **Responsive UI**: Clean, user-friendly interface with CSS styling

## Notes

- The application uses `hibernate.hbm2ddl.auto=update`, which automatically creates/updates the database schema
- The default database connection URL includes `createDatabaseIfNotExist=true`, so the database will be created automatically if it doesn't exist
- Update the MySQL username and password in `hibernate.cfg.xml` according to your setup
