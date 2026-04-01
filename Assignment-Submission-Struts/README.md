# Assignment Submission System (Struts 1)

This project implements a university assignment submission system using the Struts framework.

## Features Implemented

1. Web form fields:
   - Student Name
   - Student ID
   - Course Name
   - Assignment Title
   - Assignment Content (text area)
2. Validation rules:
   - All fields mandatory
   - Student ID format must be `S12345` (regex: `^S\\d{5}$`)
   - Assignment content must contain at least 300 words
3. Struts processing:
   - Form Bean: `AssignmentForm`
   - Action Classes: `DisplayAssignmentFormAction`, `SubmitAssignmentAction`
4. Persistence:
   - Stored in H2 database table `assignment_submissions`
5. Success page:
   - Confirmation message shown after successful insertion

## Tech Stack

- Java 8
- Maven
- Struts 1.3.10
- JSP + Struts taglib
- H2 database (file-based)

## Build

```bash
mvn clean package
```

WAR output:

- `target/assignment-submission-struts.war`

## Run

Deploy the WAR to a servlet container (Tomcat 8/9 compatible with servlet 2.5+ settings).

Open:

- `http://localhost:8080/assignment-submission-struts/assignmentForm.do`

## Project Structure

- `src/main/java/com/university/assignment/form/AssignmentForm.java`
- `src/main/java/com/university/assignment/action/SubmitAssignmentAction.java`
- `src/main/java/com/university/assignment/dao/AssignmentDao.java`
- `src/main/webapp/WEB-INF/struts-config.xml`
- `src/main/webapp/WEB-INF/jsp/assignmentForm.jsp`
- `src/main/webapp/WEB-INF/jsp/success.jsp`
