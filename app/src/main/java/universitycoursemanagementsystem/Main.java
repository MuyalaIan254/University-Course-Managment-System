package universitycoursemanagementsystem;

import universitycoursemanagementsystem.authentication.Login;
import universitycoursemanagementsystem.authentication.Register;
import universitycoursemanagementsystem.Dashboard.AdminDashboard;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    CardLayout cardLayout;
    JPanel cardPanel;

   

    public Main () {
        setTitle("University Course Management System");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize CardLayout and Main Panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add login and register screens
        cardPanel.add(new Login(this), "LogIn");
        cardPanel.add(new Register(this), "Register");
        cardPanel.add(new AdminDashboard(this), "AdminDashboard");

        add(cardPanel);

        // Show the Login page first
        cardLayout.show(cardPanel, "LogIn");

        setVisible(true);
    }

    // Method to switch screens
    public void showCard(String name) {
        cardLayout.show(cardPanel, name);
    }

    public static void main(String[] args) {
        new Main();  // Run the app
    }
}
