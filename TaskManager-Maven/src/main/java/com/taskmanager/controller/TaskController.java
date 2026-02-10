package com.taskmanager.controller;

import com.taskmanager.model.Task;
import com.taskmanager.view.TaskView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for the Task Manager application.
 * Handles business logic and coordinates between Model and View.
 */
public class TaskController {
    private List<Task> tasks;
    private TaskView view;
    private DateTimeFormatter dateFormatter;

    public TaskController(TaskView view) {
        this.tasks = new ArrayList<>();
        this.view = view;
        this.dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public void start() {
        view.displayWelcomeMessage();
        boolean running = true;

        while (running) {
            view.displayMenu();
            int choice = view.getUserChoice();

            switch (choice) {
                case 1:
                    createTask();
                    break;
                case 2:
                    viewAllTasks();
                    break;
                case 3:
                    viewPendingTasks();
                    break;
                case 4:
                    viewCompletedTasks();
                    break;
                case 5:
                    updateTask();
                    break;
                case 6:
                    markTaskComplete();
                    break;
                case 7:
                    searchTasks();
                    break;
                case 8:
                    sortTasksByPriority();
                    break;
                case 9:
                    viewTasksDueToday();
                    break;
                case 10:
                    viewOverdueTasks();
                    break;
                case 0:
                    running = false;
                    view.displayMessage("Thank you for using Task Manager. Goodbye!");
                    break;
                default:
                    view.displayError("Invalid choice. Please try again.");
            }
        }
        
        view.closeScanner();
    }

    private void createTask() {
        try {
            String title = view.getInput("Enter task title: ");
            if (title.trim().isEmpty()) {
                view.displayError("Title cannot be empty.");
                return;
            }

            String description = view.getInput("Enter task description: ");
            
            String priority = view.getInput("Enter priority (HIGH/MEDIUM/LOW): ");
            if (!isValidPriority(priority)) {
                view.displayError("Invalid priority. Using MEDIUM as default.");
                priority = "MEDIUM";
            }

            String dueDateStr = view.getInput("Enter due date (dd-MM-yyyy) or press Enter to skip: ");
            LocalDate dueDate = null;
            
            if (!dueDateStr.trim().isEmpty()) {
                try {
                    dueDate = LocalDate.parse(dueDateStr, dateFormatter);
                } catch (DateTimeParseException e) {
                    view.displayError("Invalid date format. Task created without due date.");
                }
            }

            Task task = new Task(title, description, priority, dueDate);
            tasks.add(task);
            view.displaySuccess("Task created successfully with ID: " + task.getId());
            
        } catch (Exception e) {
            view.displayError("Failed to create task: " + e.getMessage());
        }
    }

    private void viewAllTasks() {
        view.displayTasks(tasks, "ALL TASKS");
        displayStatistics();
    }

    private void viewPendingTasks() {
        List<Task> pendingTasks = tasks.stream()
                .filter(t -> !t.isCompleted())
                .collect(Collectors.toList());
        view.displayTasks(pendingTasks, "PENDING TASKS");
    }

    private void viewCompletedTasks() {
        List<Task> completedTasks = tasks.stream()
                .filter(Task::isCompleted)
                .collect(Collectors.toList());
        view.displayTasks(completedTasks, "COMPLETED TASKS");
    }

    private void updateTask() {
        String idStr = view.getInput("Enter task ID to update: ");
        try {
            int id = Integer.parseInt(idStr);
            Task task = findTaskById(id);
            
            if (task == null) {
                view.displayError("Task not found with ID: " + id);
                return;
            }

            view.displayTaskDetails(task);
            
            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Title");
            System.out.println("2. Description");
            System.out.println("3. Priority");
            System.out.println("4. Due Date");
            System.out.println("0. Cancel");
            
            int updateChoice = view.getUserChoice();
            
            switch (updateChoice) {
                case 1:
                    String newTitle = view.getInput("Enter new title: ");
                    if (!newTitle.trim().isEmpty()) {
                        task.setTitle(newTitle);
                        view.displaySuccess("Title updated successfully.");
                    }
                    break;
                case 2:
                    String newDescription = view.getInput("Enter new description: ");
                    task.setDescription(newDescription);
                    view.displaySuccess("Description updated successfully.");
                    break;
                case 3:
                    String newPriority = view.getInput("Enter new priority (HIGH/MEDIUM/LOW): ");
                    if (isValidPriority(newPriority)) {
                        task.setPriority(newPriority);
                        view.displaySuccess("Priority updated successfully.");
                    } else {
                        view.displayError("Invalid priority.");
                    }
                    break;
                case 4:
                    String newDueDateStr = view.getInput("Enter new due date (dd-MM-yyyy): ");
                    try {
                        LocalDate newDueDate = LocalDate.parse(newDueDateStr, dateFormatter);
                        task.setDueDate(newDueDate);
                        view.displaySuccess("Due date updated successfully.");
                    } catch (DateTimeParseException e) {
                        view.displayError("Invalid date format.");
                    }
                    break;
                case 0:
                    view.displayMessage("Update cancelled.");
                    break;
                default:
                    view.displayError("Invalid choice.");
            }
            
        } catch (NumberFormatException e) {
            view.displayError("Invalid task ID.");
        }
    }

    private void markTaskComplete() {
        String idStr = view.getInput("Enter task ID to mark as complete: ");
        try {
            int id = Integer.parseInt(idStr);
            Task task = findTaskById(id);
            
            if (task == null) {
                view.displayError("Task not found with ID: " + id);
                return;
            }

            if (task.isCompleted()) {
                view.displayMessage("Task is already marked as complete.");
            } else {
                task.markAsComplete();
                view.displaySuccess("Task marked as complete!");
            }
            
        } catch (NumberFormatException e) {
            view.displayError("Invalid task ID.");
        }
    }

    private void searchTasks() {
        String keyword = view.getInput("Enter search keyword (title or description): ");
        List<Task> results = tasks.stream()
                .filter(t -> t.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                           t.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        
        view.displayTasks(results, "SEARCH RESULTS FOR: \"" + keyword + "\"");
    }

    private void sortTasksByPriority() {
        List<Task> sortedTasks = new ArrayList<>(tasks);
        sortedTasks.sort(Comparator.comparingInt(Task::getPriorityValue).reversed());
        view.displayTasks(sortedTasks, "TASKS SORTED BY PRIORITY (HIGH → LOW)");
    }

    private void viewTasksDueToday() {
        List<Task> tasksDueToday = tasks.stream()
                .filter(Task::isDueToday)
                .collect(Collectors.toList());
        view.displayTasks(tasksDueToday, "TASKS DUE TODAY");
    }

    private void viewOverdueTasks() {
        List<Task> overdueTasks = tasks.stream()
                .filter(Task::isOverdue)
                .collect(Collectors.toList());
        view.displayTasks(overdueTasks, "OVERDUE TASKS");
    }

    private Task findTaskById(int id) {
        return tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private boolean isValidPriority(String priority) {
        String upperPriority = priority.toUpperCase();
        return upperPriority.equals("HIGH") || 
               upperPriority.equals("MEDIUM") || 
               upperPriority.equals("LOW");
    }

    private void displayStatistics() {
        int total = tasks.size();
        long pending = tasks.stream().filter(t -> !t.isCompleted()).count();
        long completed = tasks.stream().filter(Task::isCompleted).count();
        view.displayStatistics(total, (int)pending, (int)completed);
    }
}
