import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Model class representing a Contact in the Address Book application.
 * Contains contact details and methods to manipulate contact data.
 */
public class Contact {
    private static int idCounter = 1;
    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String category; // FAMILY, FRIEND, WORK, OTHER
    private LocalDateTime createdDate;
    private LocalDateTime lastModified;

    // Constructor
    public Contact(String name, String phoneNumber, String email, String address, String category) {
        this.id = idCounter++;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.category = category != null ? category.toUpperCase() : "OTHER";
        this.createdDate = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
        this.lastModified = LocalDateTime.now();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.lastModified = LocalDateTime.now();
    }

    public void setEmail(String email) {
        this.email = email;
        this.lastModified = LocalDateTime.now();
    }

    public void setAddress(String address) {
        this.address = address;
        this.lastModified = LocalDateTime.now();
    }

    public void setCategory(String category) {
        this.category = category != null ? category.toUpperCase() : "OTHER";
        this.lastModified = LocalDateTime.now();
    }

    // Business methods
    public boolean hasEmail() {
        return email != null && !email.trim().isEmpty();
    }

    public boolean hasAddress() {
        return address != null && !address.trim().isEmpty();
    }

    public boolean matchesName(String searchTerm) {
        return name != null && name.toLowerCase().contains(searchTerm.toLowerCase());
    }

    public boolean matchesPhone(String searchTerm) {
        return phoneNumber != null && phoneNumber.contains(searchTerm);
    }

    public boolean matchesEmail(String searchTerm) {
        return email != null && email.toLowerCase().contains(searchTerm.toLowerCase());
    }

    public boolean matchesCategory(String searchCategory) {
        return category != null && category.equalsIgnoreCase(searchCategory);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String addressStr = hasAddress() ? address : "No address";
        String emailStr = hasEmail() ? email : "No email";
        
        return String.format("ID: %d | Name: %-20s | Phone: %-15s%n" +
                           "   Email: %-30s | Category: %-10s%n" +
                           "   Address: %s%n" +
                           "   Created: %s | Modified: %s",
                id, name, phoneNumber, emailStr, category, addressStr,
                createdDate.format(formatter), lastModified.format(formatter));
    }
}
