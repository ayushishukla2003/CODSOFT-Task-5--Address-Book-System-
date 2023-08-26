public class Contact {
    private String name;
    private String phoneNumber;
    private String emailAddress;

    // Constructor with all fields
    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    // Empty constructor
    public Contact() {
        // Default constructor
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Setter for phoneNumber
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getter for emailAddress
    public String getEmailAddress() {
        return emailAddress;
    }

    // Setter for emailAddress
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    // toString method to format the contact information
    @Override
    public String toString() {
        return "Name: " + name + "\nPhone Number: " + phoneNumber + "\nEmail Address: " + emailAddress;
    }
}
