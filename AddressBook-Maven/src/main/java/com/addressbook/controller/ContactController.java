package com.addressbook.controller;

import com.addressbook.model.Contact;
import com.addressbook.view.ContactView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for the Address Book application.
 * Handles business logic and coordinates between Model and View.
 */
public class ContactController {
    private List<Contact> contacts;
    private ContactView view;

    public ContactController(ContactView view) {
        this.contacts = new ArrayList<>();
        this.view = view;
    }

    public void start() {
        view.displayWelcomeMessage();
        boolean running = true;

        while (running) {
            view.displayMenu();
            int choice = view.getUserChoice();

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    viewAllContacts();
                    break;
                case 3:
                    searchContacts();
                    break;
                case 4:
                    updateContact();
                    break;
                case 5:
                    deleteContact();
                    break;
                case 6:
                    viewContactsByCategory();
                    break;
                case 7:
                    displayStatistics();
                    break;
                case 0:
                    running = false;
                    view.displayMessage("Thank you for using Address Book. Goodbye!");
                    break;
                default:
                    view.displayError("Invalid choice. Please try again.");
            }
        }
        
        view.closeScanner();
    }

    private void addContact() {
        try {
            String name = view.getInput("Enter contact name: ");
            if (name.trim().isEmpty()) {
                view.displayError("Name cannot be empty.");
                return;
            }

            String phoneNumber = view.getInput("Enter phone number: ");
            if (phoneNumber.trim().isEmpty()) {
                view.displayError("Phone number cannot be empty.");
                return;
            }

            // Check for duplicate phone number
            if (contacts.stream().anyMatch(c -> c.getPhoneNumber().equals(phoneNumber))) {
                view.displayError("A contact with this phone number already exists.");
                return;
            }

            String email = view.getInput("Enter email (or press Enter to skip): ");
            String address = view.getInput("Enter address (or press Enter to skip): ");
            
            view.displayCategoryMenu();
            String categoryInput = view.getInput("").trim();
            String category = parseCategoryInput(categoryInput);

            Contact contact = new Contact(name, phoneNumber, email, address, category);
            contacts.add(contact);
            view.displaySuccess("Contact added successfully with ID: " + contact.getId());
            
        } catch (Exception e) {
            view.displayError("Failed to add contact: " + e.getMessage());
        }
    }

    private void viewAllContacts() {
        List<Contact> sortedContacts = contacts.stream()
                .sorted(Comparator.comparing(Contact::getName))
                .collect(Collectors.toList());
        view.displayContacts(sortedContacts, "ALL CONTACTS (Sorted by Name)");
        displayStatistics();
    }

    private void searchContacts() {
        view.displaySearchMenu();
        int choice = view.getUserChoice();

        List<Contact> results = new ArrayList<>();
        String searchTerm;

        switch (choice) {
            case 1: // Search by name
                searchTerm = view.getInput("Enter name to search: ");
                results = contacts.stream()
                        .filter(c -> c.matchesName(searchTerm))
                        .collect(Collectors.toList());
                view.displayContacts(results, "SEARCH RESULTS - Name: \"" + searchTerm + "\"");
                break;
                
            case 2: // Search by phone
                searchTerm = view.getInput("Enter phone number to search: ");
                results = contacts.stream()
                        .filter(c -> c.matchesPhone(searchTerm))
                        .collect(Collectors.toList());
                view.displayContacts(results, "SEARCH RESULTS - Phone: \"" + searchTerm + "\"");
                break;
                
            case 3: // Search by email
                searchTerm = view.getInput("Enter email to search: ");
                results = contacts.stream()
                        .filter(c -> c.matchesEmail(searchTerm))
                        .collect(Collectors.toList());
                view.displayContacts(results, "SEARCH RESULTS - Email: \"" + searchTerm + "\"");
                break;
                
            case 4: // Search by category
                view.displayCategoryMenu();
                String categoryInput = view.getInput("").trim();
                String category = parseCategoryInput(categoryInput);
                results = contacts.stream()
                        .filter(c -> c.matchesCategory(category))
                        .collect(Collectors.toList());
                view.displayContacts(results, "SEARCH RESULTS - Category: " + category);
                break;
                
            case 0:
                view.displayMessage("Returning to main menu.");
                break;
                
            default:
                view.displayError("Invalid choice.");
        }
    }

    private void updateContact() {
        String idStr = view.getInput("Enter contact ID to update: ");
        try {
            int id = Integer.parseInt(idStr);
            Contact contact = findContactById(id);
            
            if (contact == null) {
                view.displayError("Contact not found with ID: " + id);
                return;
            }

            view.displayContactDetails(contact);
            view.displayUpdateMenu();
            
            int updateChoice = view.getUserChoice();
            
            switch (updateChoice) {
                case 1:
                    String newName = view.getInput("Enter new name: ");
                    if (!newName.trim().isEmpty()) {
                        contact.setName(newName);
                        view.displaySuccess("Name updated successfully.");
                    }
                    break;
                    
                case 2:
                    String newPhone = view.getInput("Enter new phone number: ");
                    if (!newPhone.trim().isEmpty()) {
                        // Check for duplicate
                        if (contacts.stream()
                                .anyMatch(c -> c.getId() != id && c.getPhoneNumber().equals(newPhone))) {
                            view.displayError("Another contact with this phone number already exists.");
                        } else {
                            contact.setPhoneNumber(newPhone);
                            view.displaySuccess("Phone number updated successfully.");
                        }
                    }
                    break;
                    
                case 3:
                    String newEmail = view.getInput("Enter new email: ");
                    contact.setEmail(newEmail);
                    view.displaySuccess("Email updated successfully.");
                    break;
                    
                case 4:
                    String newAddress = view.getInput("Enter new address: ");
                    contact.setAddress(newAddress);
                    view.displaySuccess("Address updated successfully.");
                    break;
                    
                case 5:
                    view.displayCategoryMenu();
                    String categoryInput = view.getInput("").trim();
                    String newCategory = parseCategoryInput(categoryInput);
                    contact.setCategory(newCategory);
                    view.displaySuccess("Category updated successfully.");
                    break;
                    
                case 0:
                    view.displayMessage("Update cancelled.");
                    break;
                    
                default:
                    view.displayError("Invalid choice.");
            }
            
        } catch (NumberFormatException e) {
            view.displayError("Invalid contact ID.");
        }
    }

    private void deleteContact() {
        String idStr = view.getInput("Enter contact ID to delete: ");
        try {
            int id = Integer.parseInt(idStr);
            Contact contact = findContactById(id);
            
            if (contact == null) {
                view.displayError("Contact not found with ID: " + id);
                return;
            }

            view.displayContactDetails(contact);
            
            if (view.confirmAction("Are you sure you want to delete this contact?")) {
                contacts.remove(contact);
                view.displaySuccess("Contact deleted successfully!");
            } else {
                view.displayMessage("Deletion cancelled.");
            }
            
        } catch (NumberFormatException e) {
            view.displayError("Invalid contact ID.");
        }
    }

    private void viewContactsByCategory() {
        view.displayCategoryMenu();
        String categoryInput = view.getInput("").trim();
        String category = parseCategoryInput(categoryInput);
        
        List<Contact> categoryContacts = contacts.stream()
                .filter(c -> c.matchesCategory(category))
                .sorted(Comparator.comparing(Contact::getName))
                .collect(Collectors.toList());
        
        view.displayContacts(categoryContacts, "CONTACTS - Category: " + category);
    }

    private void displayStatistics() {
        int total = contacts.size();
        long withEmail = contacts.stream().filter(Contact::hasEmail).count();
        long withAddress = contacts.stream().filter(Contact::hasAddress).count();
        
        long family = contacts.stream().filter(c -> c.matchesCategory("FAMILY")).count();
        long friends = contacts.stream().filter(c -> c.matchesCategory("FRIEND")).count();
        long work = contacts.stream().filter(c -> c.matchesCategory("WORK")).count();
        long other = contacts.stream().filter(c -> c.matchesCategory("OTHER")).count();
        
        view.displayStatistics(total, (int)withEmail, (int)withAddress, 
                             (int)family, (int)friends, (int)work, (int)other);
    }

    private Contact findContactById(int id) {
        return contacts.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private String parseCategoryInput(String input) {
        switch (input) {
            case "1":
                return "FAMILY";
            case "2":
                return "FRIEND";
            case "3":
                return "WORK";
            case "4":
                return "OTHER";
            default:
                return input.isEmpty() ? "OTHER" : input.toUpperCase();
        }
    }
}
