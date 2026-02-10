# Task Manager Application - Plain Java Version

## Overview
A console-based Task Manager application built using the Model-View-Controller (MVC) architecture pattern in Java.

## Architecture

### Model (Task.java)
- Represents individual tasks with properties:
  - ID (auto-generated)
  - Title
  - Description
  - Priority (HIGH, MEDIUM, LOW)
  - Completion status
  - Due date
  - Created date
- Business logic methods for task manipulation

### View (TaskView.java)
- Console-based user interface
- Displays menus, task lists, and messages
- Handles user input
- Formatted output with visual separators

### Controller (TaskController.java)
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

## How to Compile and Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher

### Compilation
```bash
javac Task.java TaskView.java TaskController.java Main.java
```

### Execution
```bash
java Main
```

## Usage Example

1. Run the application
2. Select option 1 to create a new task
3. Enter task details:
   - Title: "Complete Java assignment"
   - Description: "Implement MVC pattern"
   - Priority: HIGH
   - Due date: 31-12-2025
4. View tasks using option 2
5. Mark task as complete using option 6
6. Search for specific tasks using option 7
7. Sort tasks by priority using option 8

## Project Structure
```
TaskManager-Java/
├── Task.java           # Model component
├── TaskView.java       # View component
├── TaskController.java # Controller component
├── Main.java          # Application entry point
└── README.md          # This file
```

## Design Patterns Used
- **MVC (Model-View-Controller)**: Separates concerns into three interconnected components
- **Separation of Concerns**: Each class has a single responsibility
- **Stream API**: Used for filtering and data manipulation

## Author
Created as part of Advanced Java Programming coursework.
