package universitycoursemanagementsystem.Dashboard;




import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class BaseDashboard extends JPanel implements ActionListener {

    

    protected JButton home,profile,courses,students,instructors,settings,logout,timetable;
    protected JPanel sidebar;

    protected BaseDashboard(){


        setLayout(new MigLayout("fillx, filly", "[]", "[]20[]20[]20[]20[]20[]"));

        sidebar = new JPanel(new MigLayout("fillx, insets 20", "[]", "[]10[]10[]10[]10[]10[]10[]push[]"));
        sidebar.setPreferredSize(new Dimension(200, getHeight())); 
        sidebar.setBackground(new Color(33, 33, 33));



        home = sidebarButton("Home", "/Icon/home.png");
        profile = sidebarButton("Profile", "/Icon/profile.png");
        courses = sidebarButton("Courses", "/Icon/courses.png");
        students = sidebarButton("Students", "/Icon/students.png");
        instructors = sidebarButton("Instructors", "/Icon/Lecturer.png");
        settings = sidebarButton("Settings", "/Icon/settings.png");
        logout = sidebarButton("Logout", "/Icon/logout.png");
        timetable=sidebarButton("Timetable","/Icon/timetable.png");


    }

    protected JButton sidebarButton(String text,String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(44, 44, 44));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            // Load image from classpath
        URL imageUrl = getClass().getResource(iconPath);
        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl);
            Image scaledImage = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
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

    @Override
    public void actionPerformed(ActionEvent e) {
        // Add your action handling code here
    }
}
