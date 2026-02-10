/**
 * Main class to run the Address Book Application.
 * This application follows the Model-View-Controller (MVC) architecture pattern.
 * 
 * Architecture:
 * - Model: Contact.java - Represents the data and business logic
 * - View: ContactView.java - Handles user interface and display
 * - Controller: ContactController.java - Manages the flow between Model and View
 */
public class Main {
    public static void main(String[] args) {
        // Create the View
        ContactView view = new ContactView();
        
        // Create the Controller with the View
        ContactController controller = new ContactController(view);
        
        // Start the application
        controller.start();
    }
}
