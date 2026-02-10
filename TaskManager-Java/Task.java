import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Model class representing a Task in the Task Manager application.
 * Contains task details and methods to manipulate task data.
 */
public class Task {
    private static int idCounter = 1;
    private int id;
    private String title;
    private String description;
    private String priority; // HIGH, MEDIUM, LOW
    private boolean isCompleted;
    private LocalDate dueDate;
    private LocalDate createdDate;

    // Constructor
    public Task(String title, String description, String priority, LocalDate dueDate) {
        this.id = idCounter++;
        this.title = title;
        this.description = description;
        this.priority = priority.toUpperCase();
        this.isCompleted = false;
        this.dueDate = dueDate;
        this.createdDate = LocalDate.now();
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(String priority) {
        this.priority = priority.toUpperCase();
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    // Business methods
    public void markAsComplete() {
        this.isCompleted = true;
    }

    public void markAsIncomplete() {
        this.isCompleted = false;
    }

    public boolean isDueToday() {
        return dueDate != null && dueDate.equals(LocalDate.now());
    }

    public boolean isOverdue() {
        return dueDate != null && dueDate.isBefore(LocalDate.now()) && !isCompleted;
    }

    public int getPriorityValue() {
        switch (priority) {
            case "HIGH":
                return 3;
            case "MEDIUM":
                return 2;
            case "LOW":
                return 1;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dueDateStr = dueDate != null ? dueDate.format(formatter) : "No due date";
        String status = isCompleted ? "[COMPLETED]" : "[PENDING]";
        String overdueMarker = isOverdue() ? " [OVERDUE!]" : "";
        
        return String.format("ID: %d | %s | Priority: %-6s | Due: %s%s | %s%n   Description: %s",
                id, status, priority, dueDateStr, overdueMarker, title, description);
    }
}
