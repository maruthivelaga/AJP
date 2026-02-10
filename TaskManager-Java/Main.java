/**
 * Main class to run the Task Manager Application.
 * This application follows the Model-View-Controller (MVC) architecture pattern.
 * 
 * Architecture:
 * - Model: Task.java - Represents the data and business logic
 * - View: TaskView.java - Handles user interface and display
 * - Controller: TaskController.java - Manages the flow between Model and View
 */
public class Main {
    public static void main(String[] args) {
        // Create the View
        TaskView view = new TaskView();
        
        // Create the Controller with the View
        TaskController controller = new TaskController(view);
        
        // Start the application
        controller.start();
    }
}
