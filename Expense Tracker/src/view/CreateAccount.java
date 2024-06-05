package view;
import javax.swing.*;

import utils.GlobalVariables;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CreateAccount extends JFrame implements ActionListener {
    private JLabel headingLabel, fullNameLabel, usernameLabel, genderLabel, emailLabel, passwordLabel, confirmPasswordLabel;
    private JTextField fullNameField, usernameField, emailField;
    private JComboBox<String> genderComboBox;
    private JPasswordField passwordField, confirmPasswordField;
    private JCheckBox agreeCheckBox;
    private JButton backButton, createButton;
    private static final long serialVersionUID = 1L; // Add serialVersionUID
    public CreateAccount() {
        setTitle("Create Account");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Heading Label
        headingLabel = new JLabel("Create Account");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headingLabel.setBounds(180, 20, 200, 30);
        add(headingLabel);

        // Full Name Label and Field
        fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setBounds(50, 70, 100, 20);
        add(fullNameLabel);
        fullNameField = new JTextField();
        fullNameField.setBounds(150, 70, 200, 20);
        add(fullNameField);

        // Username Label and Field
        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 100, 100, 20);
        add(usernameLabel);
        usernameField = new JTextField();
        usernameField.setBounds(150, 100, 200, 20);
        add(usernameField);

        // Gender Label and ComboBox
        genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 130, 100, 20);
        add(genderLabel);
        String[] genders = {"Male", "Female", "Other"};
        genderComboBox = new JComboBox<>(genders);
        genderComboBox.setBounds(150, 130, 100, 20);
        add(genderComboBox);

        // Email Label and Field
        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 160, 100, 20);
        add(emailLabel);
        emailField = new JTextField();
        emailField.setBounds(150, 160, 200, 20);
        add(emailField);

        // Password Label and Field
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 190, 100, 20);
        add(passwordLabel);
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 190, 200, 20);
        add(passwordField);

        // Confirm Password Label and Field
        confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(50, 220, 120, 20);
        add(confirmPasswordLabel);
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(180, 220, 200, 20);
        add(confirmPasswordField);

        // Checkbox for agreeing to terms and conditions
        agreeCheckBox = new JCheckBox("I agree to the terms and conditions");
        agreeCheckBox.setBounds(50, 250, 300, 20);
        add(agreeCheckBox);

        // Back Button
        backButton = new JButton("Back");
        backButton.setBounds(50, 20, 100, 30);
        backButton.addActionListener(this);
        add(backButton);

        // Create Button
        createButton = new JButton("Create");
        createButton.setBounds(180, 400, 100, 30);
        createButton.addActionListener(this);
        add(createButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            // Go back to previous page
            dispose(); // Close the current window
            // Implement navigation logic to go back
        } else if (e.getSource() == createButton) {
            // Validate user input
            if (validateInput()) {
                // If input is valid, store data in database
                int userID = GlobalVariables.getUserID();
                if (storeDataInDatabase(userID)) {
                    // Show success message
                    JOptionPane.showMessageDialog(this, "Account created successfully!");
                    // Close the current window or navigate to another page
                    dispose();
                } else {
                    // Show error message
                    JOptionPane.showMessageDialog(this, "Failed to create account. Please try again later.");
                }
            }
        }
    }

    private boolean validateInput() {
        String fullName = fullNameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        

        // Check if any field is empty
        if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return false;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return false;
        }

        // Add more validation as needed (e.g., email format validation)

        // Check if terms and conditions are agreed
        if (!agreeCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please agree to the terms and conditions!");
            return false;
        }

        return true;
    }

    private boolean storeDataInDatabase(int userID) {
        String fullName = fullNameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String gender = (String) genderComboBox.getSelectedItem();

        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/expensetrack", "root", "root");

            // Create SQL statement
            String sql = "INSERT INTO user (full_name, username, email, password, gender, UserID) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, fullName);
            statement.setString(2, username);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.setString(5, gender);
            statement.setInt(6, userID); // Insert the UserID

            // Execute SQL statement
            int rowsInserted = statement.executeUpdate();

            // Close resources
            statement.close();
            connection.close();

            // Return true if data is successfully inserted
            return rowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        new CreateAccount();
    }
}
