package view;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import dao.ExpenseDAO;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Connection connection;
    private static final long serialVersionUID = 1L;

    public LoginPage() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel logoPanel = new JPanel(new BorderLayout());
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/resources/journal_logo.jpeg"));
        if (logoIcon != null) {
            JLabel logoLabel = new JLabel(logoIcon);
            logoPanel.add(logoLabel, BorderLayout.NORTH);
        }
        JLabel welcomeLabel = new JLabel("Welcome to Your Expense Tracker Application", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        logoPanel.add(welcomeLabel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            int userID = authenticate(username, password);
            if (userID != -1) {
                // Initialize DAO objects
                ExpenseDAO expenseDAO = new ExpenseDAO(connection);
                // Open expense management UI
                SwingUtilities.invokeLater(() -> {
                    ExpenseManagementUI expenseManagementUI = new ExpenseManagementUI(expenseDAO, userID);
                    expenseManagementUI.displayUI();
                });
                dispose(); // Close the login window
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);
        inputPanel.add(new JLabel());
        inputPanel.add(loginButton);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(e -> {
            CreateAccount createAccount = new CreateAccount();
            createAccount.setVisible(true);
        });
        buttonPanel.add(createAccountButton);

        add(logoPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/expensetrack", "root", "root");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private int authenticate(String username, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT UserID FROM user WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("UserID");
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

}
