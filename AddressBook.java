import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBook {
    private List<Contact> contacts = new ArrayList<>();

    // Add a contact to the address book
    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    // Remove a contact from the address book
    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    // Search for a contact by name
    public Contact searchContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    // Display all contacts in the address book
    public List<Contact> getAllContacts() {
        return contacts;
    }

    // Save contacts to a text file
    public void saveContactsToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Contact contact : contacts) {
                writer.println(contact.getName());
                writer.println(contact.getPhoneNumber());
                writer.println(contact.getEmailAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load contacts from a text file
    public void loadContactsFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Contact contact = new Contact();
                contact.setName(line);
                contact.setPhoneNumber(reader.readLine());
                contact.setEmailAddress(reader.readLine());
                contacts.add(contact);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
