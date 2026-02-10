package com.addressbook.view;

import com.addressbook.model.Contact;
import java.util.List;
import java.util.Scanner;

/**
 * View class for the Address Book application.
 * Handles all user interface and display operations.
 */
public class ContactView {
    private Scanner scanner;

    public ContactView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayWelcomeMessage() {
        System.out.println("\n==================================================");
        System.out.println("     ADDRESS BOOK APPLICATION (MVC)             ");
        System.out.println("           Maven Version                        ");
        System.out.println("==================================================\n");
    }

    public void displayMenu() {
        System.out.println("\n================= MAIN MENU ===================");
        System.out.println(" 1. Add New Contact");
        System.out.println(" 2. View All Contacts");
        System.out.println(" 3. Search Contacts");
        System.out.println(" 4. Update Contact");
        System.out.println(" 5. Delete Contact");
        System.out.println(" 6. View Contacts by Category");
        System.out.println(" 7. Display Statistics");
        System.out.println(" 0. Exit");
        System.out.println("================================================");
        System.out.print("Enter your choice: ");
    }

    public void displaySearchMenu() {
        System.out.println("\n=============== SEARCH OPTIONS ================");
        System.out.println(" 1. Search by Name");
        System.out.println(" 2. Search by Phone Number");
        System.out.println(" 3. Search by Email");
        System.out.println(" 4. Search by Category");
        System.out.println(" 0. Back to Main Menu");
        System.out.println("================================================");
        System.out.print("Enter your choice: ");
    }

    public void displayUpdateMenu() {
        System.out.println("\n============= UPDATE OPTIONS ==================");
        System.out.println(" 1. Update Name");
        System.out.println(" 2. Update Phone Number");
        System.out.println(" 3. Update Email");
        System.out.println(" 4. Update Address");
        System.out.println(" 5. Update Category");
        System.out.println(" 0. Cancel");
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

    public void displayContacts(List<Contact> contacts, String header) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println(header);
        System.out.println("=".repeat(80));
        
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact);
                System.out.println("-".repeat(80));
            }
            System.out.println("Total: " + contacts.size() + " contact(s)");
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

    public void displayContactDetails(Contact contact) {
        if (contact == null) {
            displayError("Contact not found.");
            return;
        }
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("CONTACT DETAILS");
        System.out.println("=".repeat(80));
        System.out.println(contact);
        System.out.println("=".repeat(80));
    }

    public boolean confirmAction(String message) {
        System.out.print(message + " (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("y") || response.equals("yes");
    }

    public void closeScanner() {
        scanner.close();
    }

    public void displayStatistics(int total, int withEmail, int withAddress, 
                                  int family, int friends, int work, int other) {
        System.out.println("\n============== ADDRESS BOOK STATISTICS ==============");
        System.out.println(" Total Contacts:        " + String.format("%-20d", total));
        System.out.println(" Contacts with Email:   " + String.format("%-20d", withEmail));
        System.out.println(" Contacts with Address: " + String.format("%-20d", withAddress));
        System.out.println("\n Category Breakdown:");
        System.out.println(" - Family:              " + String.format("%-20d", family));
        System.out.println(" - Friends:             " + String.format("%-20d", friends));
        System.out.println(" - Work:                " + String.format("%-20d", work));
        System.out.println(" - Other:               " + String.format("%-20d", other));
        System.out.println("======================================================");
    }

    public void displayCategoryMenu() {
        System.out.println("\n============ SELECT CATEGORY ================");
        System.out.println(" 1. Family");
        System.out.println(" 2. Friends");
        System.out.println(" 3. Work");
        System.out.println(" 4. Other");
        System.out.println("=============================================");
        System.out.print("Enter category (or type custom): ");
    }
}
