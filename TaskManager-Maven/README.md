# Task Manager Application - Maven Version

## Overview
A console-based Task Manager application built using the Model-View-Controller (MVC) architecture pattern in Java with Maven project management.

## Architecture

### Model (com.taskmanager.model.Task)
- Represents individual tasks with properties:
  - ID (auto-generated)
  - Title
  - Description
  - Priority (HIGH, MEDIUM, LOW)
  - Completion status
  - Due date
  - Created date
- Business logic methods for task manipulation

### View (com.taskmanager.view.TaskView)
- Console-based user interface
- Displays menus, task lists, and messages
- Handles user input
- Formatted output with visual separators

### Controller (com.taskmanager.controller.TaskController)
- Coordinates between Model and View
- Handles business logic and user actions
- Manages the task collection
- Implements all application features

## Features

### Core Features
1. ✅ Create new tasks
2. ✅ View all tasks
3. ✅ View pending tasks
4. ✅ View completed tasks
5. ✅ Update task details (title, description, priority, due date)
6. ✅ Mark tasks as complete

### Additional Features
7. ✅ Search tasks by keyword
8. ✅ Sort tasks by priority
9. ✅ View tasks due today
10. ✅ View overdue tasks
11. ✅ Task statistics
12. ✅ Due date management
13. ✅ Overdue task detection

## Prerequisites
- Java Development Kit (JDK) 11 or higher
- Apache Maven 3.6 or higher

## Project Structure
```
TaskManager-Maven/
├── pom.xml                                    # Maven configuration file
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── taskmanager/
│                   ├── Main.java              # Application entry point
│                   ├── model/
│                   │   └── Task.java          # Model component
│                   ├── view/
│                   │   └── TaskView.java      # View component
│                   └── controller/
│                       └── TaskController.java # Controller component
└── README.md                                  # This file
```

## How to Build and Run

### Option 1: Using Maven Commands

#### Compile the project
```bash
cd TaskManager-Maven
mvn clean compile
```

#### Run the application
```bash
mvn exec:java -Dexec.mainClass="com.taskmanager.Main"
```

#### Build executable JAR
```bash
mvn clean package
```

#### Run the JAR file
```bash
java -jar target/taskmanager-mvc-1.0.0.jar
```

### Option 2: Using IDE
1. Import the project as a Maven project in your IDE (IntelliJ IDEA, Eclipse, VS Code)
2. Wait for Maven to download dependencies
3. Run the `Main.java` class

## Maven Commands Reference

| Command | Description |
|---------|-------------|
| `mvn clean` | Clean the project (remove target directory) |
| `mvn compile` | Compile the source code |
| `mvn test` | Run unit tests |
| `mvn package` | Create JAR file |
| `mvn install` | Install JAR to local Maven repository |
| `mvn clean package` | Clean and create fresh build |

## Usage Example

1. Run the application using one of the methods above
2. Select option 1 to create a new task
3. Enter task details:
   - Title: "Complete Java assignment"
   - Description: "Implement MVC pattern with Maven"
   - Priority: HIGH
   - Due date: 31-12-2025
4. View tasks using option 2
5. Mark task as complete using option 6
6. Search for specific tasks using option 7
7. Sort tasks by priority using option 8

## Design Patterns Used
- **MVC (Model-View-Controller)**: Separates concerns into three interconnected components
- **Separation of Concerns**: Each class has a single responsibility
- **Stream API**: Used for filtering and data manipulation
- **Package Organization**: Proper package structure for maintainability

## Maven Dependencies
- JUnit 4.13.2 (for testing - optional)

## Build Plugins
- Maven Compiler Plugin 3.11.0
- Maven Jar Plugin 3.3.0
- Maven Shade Plugin 3.5.0 (for creating executable JAR)

## Author
Created as part of Advanced Java Programming coursework.

## License
This project is created for educational purposes.
