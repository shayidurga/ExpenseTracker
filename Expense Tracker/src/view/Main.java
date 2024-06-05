package view;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Initialize the LoginPage
                LoginPage loginPage = new LoginPage();
                loginPage.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
