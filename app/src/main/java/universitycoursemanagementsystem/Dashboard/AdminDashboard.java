package universitycoursemanagementsystem.Dashboard;

import universitycoursemanagementsystem .Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import net.miginfocom.swing.MigLayout;


public class AdminDashboard extends BaseDashboard implements ActionListener {
    
    Main parent;

    public AdminDashboard(Main parent){
        super();
        this.parent=parent;

        setLayout(new MigLayout("fillx, filly", "[]", "[]20[]20[]20[]20[]20[]"));

        JPanel header= new JPanel(new MigLayout("fillx, insets 20", "[]push[]", "[]"));
        header.setBackground(new Color(33, 33, 33));
        header.add(new JLabel("Admin Dashboard"), "span, center, wrap");
        header.setFont(new Font("Arial", Font  .BOLD, 24));
        header.setForeground(Color .WHITE);

         
        JPanel content = new JPanel(new MigLayout("fillx, insets 20", "[]", "[]"));
        content .setBackground(new Color(33, 33, 33));

        super.sidebar.add(home, "growx, wrap");
        super.sidebar.add(profile, "growx, wrap");
        super.sidebar.add(courses, "growx, wrap");
        super.sidebar.add(students, "growx, wrap");
        super.sidebar.add(instructors, "growx, wrap");
        super.sidebar.add(settings, "growx, wrap");

        super.sidebar.add(Box.createVerticalGlue(), "push, wrap");

        super.sidebar.add(logout, "growx");

        add(sidebar);

    }
    
}
   