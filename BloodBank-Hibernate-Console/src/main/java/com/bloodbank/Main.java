package com.bloodbank;

import com.bloodbank.dao.BloodUnitDAO;
import com.bloodbank.entity.BloodUnit;
import com.bloodbank.entity.BloodUnit.*;
import com.bloodbank.util.HibernateUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Main class for Blood Bank Inventory System
 */
public class Main {
    private static BloodUnitDAO bloodUnitDAO = new BloodUnitDAO();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("  BLOOD BANK INVENTORY & ISSUANCE SYSTEM");
        System.out.println("===========================================");
        
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addBloodUnit();
                    break;
                case 2:
                    viewAllBloodUnits();
                    break;
                case 3:
                    viewBloodUnitById();
                    break;
                case 4:
                    updateBloodUnit();
                    break;
                case 5:
                    deleteBloodUnit();
                    break;
                case 6:
                    searchBloodUnits();
                    break;
                case 7:
                    issueBloodUnit();
                    break;
                case 8:
                    viewExpiredUnits();
                    break;
                case 9:
                    generateSummaryReport();
                    break;
                case 10:
                    running = false;
                    System.out.println("\nShutting down...");
                    HibernateUtil.shutdown();
                    System.out.println("Thank you for using Blood Bank Inventory System!");
                    break;
                default:
                    System.out.println("\nInvalid choice! Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("\n===========================================");
        System.out.println("MENU:");
        System.out.println("1. Add New Blood Unit");
        System.out.println("2. View All Blood Units");
        System.out.println("3. View Blood Unit by ID");
        System.out.println("4. Update Blood Unit");
        System.out.println("5. Delete Blood Unit");
        System.out.println("6. Search Blood Units");
        System.out.println("7. Issue Blood Unit");
        System.out.println("8. View Expired Units");
        System.out.println("9. Generate Summary Report");
        System.out.println("10. Exit");
        System.out.println("===========================================");
    }
    
    private static void addBloodUnit() {
        System.out.println("\n--- ADD NEW BLOOD UNIT ---");
        
        BloodType bloodType = selectBloodType();
        Rhesus rhesus = selectRhesus();
        
        System.out.print("Enter Donor ID: ");
        String donorId = scanner.nextLine();
        
        LocalDate collectionDate = getDateInput("Enter Collection Date (yyyy-MM-dd): ");
        LocalDate expiryDate = getDateInput("Enter Expiry Date (yyyy-MM-dd): ");
        
        Status status = selectStatus();
        
        BloodUnit bloodUnit = new BloodUnit(bloodType, rhesus, donorId, collectionDate, expiryDate, status);
        bloodUnitDAO.addBloodUnit(bloodUnit);
    }
    
    private static void viewAllBloodUnits() {
        System.out.println("\n--- ALL BLOOD UNITS ---");
        List<BloodUnit> units = bloodUnitDAO.getAllBloodUnits();
        
        if (units == null || units.isEmpty()) {
            System.out.println("No blood units found.");
        } else {
            System.out.println("Total Units: " + units.size());
            System.out.println("-------------------------------------------");
            for (BloodUnit unit : units) {
                System.out.println(unit);
            }
        }
    }
    
    private static void viewBloodUnitById() {
        System.out.println("\n--- VIEW BLOOD UNIT BY ID ---");
        Long unitId = getLongInput("Enter Unit ID: ");
        
        BloodUnit unit = bloodUnitDAO.getBloodUnitById(unitId);
        if (unit != null) {
            System.out.println("\n" + unit);
        } else {
            System.out.println("Blood unit not found!");
        }
    }
    
    private static void updateBloodUnit() {
        System.out.println("\n--- UPDATE BLOOD UNIT ---");
        Long unitId = getLongInput("Enter Unit ID to update: ");
        
        BloodUnit unit = bloodUnitDAO.getBloodUnitById(unitId);
        if (unit == null) {
            System.out.println("Blood unit not found!");
            return;
        }
        
        System.out.println("Current Details: " + unit);
        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Status");
        System.out.println("2. Expiry Date");
        System.out.println("3. All Details");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                unit.setStatus(selectStatus());
                bloodUnitDAO.updateBloodUnit(unit);
                break;
            case 2:
                unit.setExpiryDate(getDateInput("Enter new Expiry Date (yyyy-MM-dd): "));
                bloodUnitDAO.updateBloodUnit(unit);
                break;
            case 3:
                unit.setBloodType(selectBloodType());
                unit.setRhesus(selectRhesus());
                System.out.print("Enter Donor ID: ");
                unit.setDonorId(scanner.nextLine());
                unit.setCollectionDate(getDateInput("Enter Collection Date (yyyy-MM-dd): "));
                unit.setExpiryDate(getDateInput("Enter Expiry Date (yyyy-MM-dd): "));
                unit.setStatus(selectStatus());
                bloodUnitDAO.updateBloodUnit(unit);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    private static void deleteBloodUnit() {
        System.out.println("\n--- DELETE BLOOD UNIT ---");
        Long unitId = getLongInput("Enter Unit ID to delete: ");
        
        BloodUnit unit = bloodUnitDAO.getBloodUnitById(unitId);
        if (unit != null) {
            System.out.println("Unit to be deleted: " + unit);
            System.out.print("Are you sure? (yes/no): ");
            String confirm = scanner.nextLine();
            
            if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
                bloodUnitDAO.deleteBloodUnit(unitId);
            } else {
                System.out.println("Deletion cancelled.");
            }
        }
    }
    
    private static void searchBloodUnits() {
        System.out.println("\n--- SEARCH BLOOD UNITS ---");
        System.out.println("1. Search by Blood Type");
        System.out.println("2. Search by Rhesus Factor");
        System.out.println("3. Search by Blood Type & Rhesus");
        System.out.println("4. Search by Status");
        
        int choice = getIntInput("Enter your choice: ");
        List<BloodUnit> results = null;
        
        switch (choice) {
            case 1:
                results = bloodUnitDAO.searchByBloodType(selectBloodType());
                break;
            case 2:
                results = bloodUnitDAO.searchByRhesus(selectRhesus());
                break;
            case 3:
                BloodType bloodType = selectBloodType();
                Rhesus rhesus = selectRhesus();
                results = bloodUnitDAO.searchByBloodTypeAndRhesus(bloodType, rhesus);
                break;
            case 4:
                results = bloodUnitDAO.getBloodUnitsByStatus(selectStatus());
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }
        
        if (results != null && !results.isEmpty()) {
            System.out.println("\nSearch Results: " + results.size() + " unit(s) found");
            System.out.println("-------------------------------------------");
            for (BloodUnit unit : results) {
                System.out.println(unit);
            }
        } else {
            System.out.println("No blood units found matching your criteria.");
        }
    }
    
    private static void issueBloodUnit() {
        System.out.println("\n--- ISSUE BLOOD UNIT ---");
        Long unitId = getLongInput("Enter Unit ID to issue: ");
        bloodUnitDAO.issueBloodUnit(unitId);
    }
    
    private static void viewExpiredUnits() {
        System.out.println("\n--- EXPIRED BLOOD UNITS ---");
        List<BloodUnit> expiredUnits = bloodUnitDAO.getExpiredUnits();
        
        if (expiredUnits == null || expiredUnits.isEmpty()) {
            System.out.println("No expired units found.");
        } else {
            System.out.println("Total Expired Units: " + expiredUnits.size());
            System.out.println("-------------------------------------------");
            for (BloodUnit unit : expiredUnits) {
                System.out.println(unit);
            }
        }
    }
    
    private static void generateSummaryReport() {
        System.out.println("\n--- BLOOD INVENTORY SUMMARY REPORT ---");
        System.out.println("Available Units by Blood Type and Rhesus Factor");
        System.out.println("-------------------------------------------");
        
        Map<String, Long> summary = bloodUnitDAO.getSummaryReport();
        
        if (summary.isEmpty()) {
            System.out.println("No available blood units in inventory.");
        } else {
            long total = 0;
            for (Map.Entry<String, Long> entry : summary.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue() + " units");
                total += entry.getValue();
            }
            System.out.println("-------------------------------------------");
            System.out.println("Total Available Units: " + total);
        }
    }
    
    // Helper methods
    private static BloodType selectBloodType() {
        System.out.println("Select Blood Type:");
        System.out.println("1. A");
        System.out.println("2. B");
        System.out.println("3. AB");
        System.out.println("4. O");
        int choice = getIntInput("Enter choice: ");
        
        switch (choice) {
            case 1: return BloodType.A;
            case 2: return BloodType.B;
            case 3: return BloodType.AB;
            case 4: return BloodType.O;
            default:
                System.out.println("Invalid choice! Defaulting to O");
                return BloodType.O;
        }
    }
    
    private static Rhesus selectRhesus() {
        System.out.println("Select Rhesus Factor:");
        System.out.println("1. Positive (+)");
        System.out.println("2. Negative (-)");
        int choice = getIntInput("Enter choice: ");
        
        return (choice == 2) ? Rhesus.NEGATIVE : Rhesus.POSITIVE;
    }
    
    private static Status selectStatus() {
        System.out.println("Select Status:");
        System.out.println("1. AVAILABLE");
        System.out.println("2. RESERVED");
        System.out.println("3. ISSUED");
        System.out.println("4. QUARANTINED");
        int choice = getIntInput("Enter choice: ");
        
        switch (choice) {
            case 1: return Status.AVAILABLE;
            case 2: return Status.RESERVED;
            case 3: return Status.ISSUED;
            case 4: return Status.QUARANTINED;
            default:
                System.out.println("Invalid choice! Defaulting to AVAILABLE");
                return Status.AVAILABLE;
        }
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }
    
    private static Long getLongInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }
    
    private static LocalDate getDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String dateStr = scanner.nextLine();
                return LocalDate.parse(dateStr, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please use yyyy-MM-dd");
            }
        }
    }
}
