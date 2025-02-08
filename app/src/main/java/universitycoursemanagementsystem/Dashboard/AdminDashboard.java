package universitycoursemanagementsystem.Dashboard;

import universitycoursemanagementsystem.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.EventListener;


public class AdminDashboard extends JPanel implements EventListener {

    Main parent;

    public AdminDashboard(Main parent){
        this.parent =parent;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.CENTER;

        JButton addCourse = new JButton("Add Course");
        JButton viewCourse = new JButton("View Course");
        JButton addInstructor = new JButton("Add Instructor");
        JButton viewInstructor = new JButton("View Instructor");
        JButton addStudent = new JButton("Add Student");
        JButton viewStudent = new JButton("View Student");
        JButton logout = new JButton("Logout");

        gbc.gridx = 0;gbc.gridy = 0;add(addCourse, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(viewCourse, gbc);

    }


    
}
