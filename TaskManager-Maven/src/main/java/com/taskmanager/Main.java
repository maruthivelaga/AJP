package com.taskmanager;

import com.taskmanager.view.TaskView;
import com.taskmanager.controller.TaskController;

/**
 * Main class to run the Task Manager Application.
 * This application follows the Model-View-Controller (MVC) architecture pattern.
 * 
 * Architecture:
 * - Model: com.taskmanager.model.Task - Represents the data and business logic
 * - View: com.taskmanager.view.TaskView - Handles user interface and display
 * - Controller: com.taskmanager.controller.TaskController - Manages the flow between Model and View
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
