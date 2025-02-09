package universitycoursemanagementsystem.Dashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.EventListener;
import net.miginfocom.swing.MigLayout;
import java.net.URL;
import universitycoursemanagementsystem.Main;



public class AdminDashboard extends JPanel implements EventListener {

    JButton home,profile, courses, students, instructors, logout,settings;

    Main parent;

    

    public AdminDashboard(Main parent){
        this.parent =parent;

        setLayout(new MigLayout("fillx, filly", "[]", "[]20[]20[]20[]20[]20[]"));

        JPanel sidebar = new JPanel(new MigLayout("fillx, insets 20", "[]", "[]20[]20[]20[]20[]20[]20[]push[]"));
        sidebar.setBackground(new Color(33, 33, 33));

        JPanel header= new JPanel(new MigLayout("fillx, insets 20", "[]push[]", "[]"));
        header.setBackground(new Color(33, 33, 33));
        header.add(new JLabel("Admin Dashboard"), "span, center, wrap");
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setForeground(Color.WHITE);
        
        JPanel content = new JPanel(new MigLayout("fillx, insets 20", "[]", "[]"));
        content.setBackground(new Color(33, 33, 33));

        

        home = sidebarButton("Home", "/Icon/home.png");
        profile = sidebarButton("Profile", "/Icon/profile.png");
        courses = sidebarButton("Courses", "/Icon/courses.png");
        students = sidebarButton("Students", "/Icon/students.png");
        instructors = sidebarButton("Instructors", "/Icon/Lecturer.png");
        settings = sidebarButton("Settings", "/Icon/settings.png");
        logout = sidebarButton("Logout", "/Icon/logout.png");


        sidebar.add(home, "growx, wrap");
        sidebar.add(profile, "growx, wrap");
        sidebar.add(courses, "growx, wrap");
        sidebar.add(students, "growx, wrap");
        sidebar.add(instructors, "growx, wrap");
        sidebar.add(settings, "growx, wrap");
        sidebar.add(logout, "growx");


        home.addActionListener(this::actionPerformed);
        courses.addActionListener(this::actionPerformed);
        students.addActionListener(this::actionPerformed);
        instructors.addActionListener(this::actionPerformed);
        logout.addActionListener(this::actionPerformed);

        add(sidebar, "spany, growy");
        add(header, "span, growx");
        add(content, "span, grow");



    }

    private JButton sidebarButton(String text,String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(44, 44, 44));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Load image from classpath
        URL imageUrl = getClass().getResource(iconPath);
        if (imageUrl != null) {
            button.setIcon(new ImageIcon(imageUrl));
        } else {
            System.err.println("Error: Image not found -> " + iconPath);
        }

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(66, 66, 66));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(44, 44, 44));
            }
        });

        return button;
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == home) {
            parent.showCard("AdminDashboard");
        } else if (e.getSource() == courses) {
            parent.showCard("Courses");
        } else if (e.getSource() == students) {
            parent.showCard("Students");
        } else if (e.getSource() == instructors) {
            parent.showCard("Instructors");
        } else if (e.getSource() == logout) {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            parent.showCard("LogIn");
        }
            parent.showCard("LogIn");
        }
    }
}
