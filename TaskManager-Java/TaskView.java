import java.util.List;
import java.util.Scanner;

/**
 * View class for the Task Manager application.
 * Handles all user interface and display operations.
 */
public class TaskView {
    private Scanner scanner;

    public TaskView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayWelcomeMessage() {
        System.out.println("\n==================================================");
        System.out.println("     TASK MANAGER APPLICATION (MVC)             ");
        System.out.println("==================================================\n");
    }

    public void displayMenu() {
        System.out.println("\n================= MAIN MENU ===================");
        System.out.println(" 1. Create New Task");
        System.out.println(" 2. View All Tasks");
        System.out.println(" 3. View Pending Tasks");
        System.out.println(" 4. View Completed Tasks");
        System.out.println(" 5. Update Task Details");
        System.out.println(" 6. Mark Task as Complete");
        System.out.println(" 7. Search Tasks");
        System.out.println(" 8. Sort Tasks by Priority");
        System.out.println(" 9. View Tasks Due Today");
        System.out.println(" 10. View Overdue Tasks");
        System.out.println(" 0. Exit");
        System.out.println("================================================");
        System.out.print("Enter your choice: ");
    }

    public int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void displayTasks(List<Task> tasks, String header) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println(header);
        System.out.println("=".repeat(80));
        
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (Task task : tasks) {
                System.out.println(task);
                System.out.println("-".repeat(80));
            }
            System.out.println("Total: " + tasks.size() + " task(s)");
        }
    }

    public void displayMessage(String message) {
        System.out.println("\n>>> " + message);
    }

    public void displayError(String error) {
        System.out.println("\n[ERROR]: " + error);
    }

    public void displaySuccess(String message) {
        System.out.println("\n[SUCCESS]: " + message);
    }

    public void displayTaskDetails(Task task) {
        if (task == null) {
            displayError("Task not found.");
            return;
        }
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TASK DETAILS");
        System.out.println("=".repeat(80));
        System.out.println(task);
        System.out.println("=".repeat(80));
    }

    public void closeScanner() {
        scanner.close();
    }

    public void displayStatistics(int total, int pending, int completed) {
        System.out.println("\n=============== TASK STATISTICS =================");
        System.out.println(" Total Tasks:     " + String.format("%-30d", total));
        System.out.println(" Pending Tasks:   " + String.format("%-30d", pending));
        System.out.println(" Completed Tasks: " + String.format("%-30d", completed));
        System.out.println("==================================================");
    }
}
