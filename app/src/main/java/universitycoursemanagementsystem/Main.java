package universitycoursemanagementsystem;

import universitycoursemanagementsystem.authentication.Login;
import universitycoursemanagementsystem.authentication.Register;
import universitycoursemanagementsystem.Dashboard.AdminDashboard;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import com.formdev.flatlaf.FlatDarkLaf;


public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;


    public Main () {
        setTitle("University Course Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        
        // Set the title icon
        URL iconURL = getClass().getResource("/Icon/MainIcon.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            setIconImage(icon.getImage());
        }

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
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf()); // FlatLightLaf for light mode
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Main();  // Run the app
    }
}
