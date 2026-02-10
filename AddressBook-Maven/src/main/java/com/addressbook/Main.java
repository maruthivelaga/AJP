package com.addressbook;

import com.addressbook.view.ContactView;
import com.addressbook.controller.ContactController;

/**
 * Main class to run the Address Book Application.
 * This application follows the Model-View-Controller (MVC) architecture pattern.
 * 
 * Architecture:
 * - Model: com.addressbook.model.Contact - Represents the data and business logic
 * - View: com.addressbook.view.ContactView - Handles user interface and display
 * - Controller: com.addressbook.controller.ContactController - Manages the flow between Model and View
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
