//for better view use full screen 


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressBookGUI extends JFrame {
    private AddressBook addressBook = new AddressBook();

    private JTextField nameField = new JTextField(20);
    private JTextField phoneField = new JTextField(20);
    private JTextField emailField = new JTextField(20);
    private JTextArea displayArea = new JTextArea(10, 40);

    private String PhoneNumber;

    public AddressBookGUI() {
        setTitle("Address Book");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Phone Number:"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Email Address:"));
        inputPanel.add(emailField);

        JButton addButton = new JButton("Add Contact");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });

        JButton removeButton = new JButton("Remove Contact");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeContact();
            }
        });

        JButton searchButton = new JButton("Search Contact");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchContact();
            }
        });

        JButton editButton = new JButton("Edit Contact");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editContact();
            }
        });

        JButton displayButton = new JButton("Display Contacts");
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayContacts();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(editButton);
        buttonPanel.add(displayButton);

        JScrollPane scrollPane = new JScrollPane(displayArea);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(inputPanel, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);



        // JButton saveButton = new JButton("Save Contacts");
        // saveButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         saveContactsToFile();
        //     }
        // });

        // JButton loadButton = new JButton("Load Contacts");
        // loadButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         loadContactsFromFile();
        //         displayContacts(); // Refresh the display after loading
        //     }
        // });

        // buttonPanel.add(saveButton);
        // buttonPanel.add(loadButton);



        JButton saveLoadButton = new JButton("File Save/Load");
        saveLoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFileSaveLoadGUI();
            }
        });

        buttonPanel.add(saveLoadButton);
    }



    private boolean isValidPhoneNumber(String phoneNumber) {
        // Define a regular expression pattern for a valid phone number
        // Allows for 10 digits and an optional country code (e.g., +91)
        String pattern = "^(\\+\\d{1,3})?\\d{10}$";

        // Use Pattern and Matcher classes for regular expression matching
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(phoneNumber);

        // Check if the input matches the pattern
        return matcher.matches();
    }

    private void addContact() {
        String name = nameField.getText();
        String phoneNumber = phoneField.getText();
        String emailAddress = emailField.getText();

        if (!name.isEmpty() && isValidPhoneNumber(phoneNumber) && !emailAddress.isEmpty()) {
            Contact contact = new Contact(name, phoneNumber, emailAddress);
            addressBook.addContact(contact);
            nameField.setText("");
            phoneField.setText("");
            emailField.setText("");
            JOptionPane.showMessageDialog(this, "Contact has been added.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a valid phone number (10 digits).", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void removeContact() {
        String nameToRemove = nameField.getText().trim();
        Contact contactToRemove = addressBook.searchContact(nameToRemove);

        if (contactToRemove != null) {
            addressBook.removeContact(contactToRemove);
            nameField.setText("");
            phoneField.setText("");
            emailField.setText("");
            displayContacts(); // Refresh the display after removal
            JOptionPane.showMessageDialog(this, "Contact has been removed.", "Success", JOptionPane.INFORMATION_MESSAGE);
 
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchContact() {
        String nameToSearch = nameField.getText();
        Contact searchedContact = addressBook.searchContact(nameToSearch);

        if (searchedContact != null) {
            nameField.setText(searchedContact.getName());
            phoneField.setText(searchedContact.getPhoneNumber());
            emailField.setText(searchedContact.getEmailAddress());

             // Display a message with the contact details
        String message = "Contact Found:\n" +
        "Name: " + searchedContact.getName() + "\n" +
        "Phone Number: " + searchedContact.getPhoneNumber() + "\n" +
        "Email Address: " + searchedContact.getEmailAddress();
JOptionPane.showMessageDialog(this, message, "Contact Found", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editContact() {
        String nameToEdit = nameField.getText();
        Contact contactToEdit = addressBook.searchContact(nameToEdit);

        if (contactToEdit != null) {
            String newName = nameField.getText();
            String newPhone = phoneField.getText();
            String newEmail = emailField.getText();

            contactToEdit.setName(newName);
            contactToEdit.setPhoneNumber(newPhone);
            contactToEdit.setEmailAddress(newEmail);

            nameField.setText("");
            phoneField.setText("");
            emailField.setText("");
            displayContacts(); // Refresh the display after editing
            JOptionPane.showMessageDialog(this, "Contact has been edited.", "Success", JOptionPane.INFORMATION_MESSAGE);

            if (isValidPhoneNumber(PhoneNumber)) {
                // ... Existing code ...
                nameField.setText("");
                phoneField.setText("");
                emailField.setText("");
                displayContacts(); // Refresh the display after editing
                JOptionPane.showMessageDialog(this, "Contact has been edited.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid phone number (10 digits).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    


    private void displayContacts() {
        List<Contact> contacts = addressBook.getAllContacts();
        StringBuilder displayText = new StringBuilder();

        for (Contact contact : contacts) {
            displayText.append(contact.toString()).append("\n\n");
        }

        displayArea.setText(displayText.toString());
        JOptionPane.showMessageDialog(this, "Contacts have been displayed.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
 
    


    private void openFileSaveLoadGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FileSaveLoadGUI fileSaveLoadGUI = new FileSaveLoadGUI();
                fileSaveLoadGUI.setVisible(true);
            }
        });
    }


    // private void saveContactsToFile() {
    //     JFileChooser fileChooser = new JFileChooser();
    //     int result = fileChooser.showSaveDialog(this);
    
    //     if (result == JFileChooser.APPROVE_OPTION) {
    //         File selectedFile = fileChooser.getSelectedFile();
    //         String fileName = selectedFile.getAbsolutePath();
    
    //         try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
    //             outputStream.writeObject(addressBook);
    //             JOptionPane.showMessageDialog(this, "Contacts have been saved to the file.", "Success", JOptionPane.INFORMATION_MESSAGE);
    //         } catch (IOException ex) {
    //             ex.printStackTrace();
    //             JOptionPane.showMessageDialog(this, "Error saving contacts to the file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    //         }
    //     }
    // }
    
    // private void loadContactsFromFile() {
    //     JFileChooser fileChooser = new JFileChooser();
    //     int result = fileChooser.showOpenDialog(this);
    
    //     if (result == JFileChooser.APPROVE_OPTION) {
    //         File selectedFile = fileChooser.getSelectedFile();
    //         String fileName = selectedFile.getAbsolutePath();
    
    //         try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
    //             AddressBook loadedAddressBook = (AddressBook) inputStream.readObject();
    //             addressBook = loadedAddressBook;
    //             JOptionPane.showMessageDialog(this, "Contacts have been loaded from the file.", "Success", JOptionPane.INFORMATION_MESSAGE);
    //         } catch (IOException | ClassNotFoundException ex) {
    //             ex.printStackTrace();
    //             JOptionPane.showMessageDialog(this, "Error loading contacts from the file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    //         }
    //     }
    // }
    



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AddressBookGUI addressBookGUI = new AddressBookGUI();
                addressBookGUI.setVisible(true);
            }
        });
    }
}
