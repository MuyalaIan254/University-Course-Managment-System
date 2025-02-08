package universitycoursemanagementsystem.Dashboard;

import universitycoursemanagementsystem.Main;

import javax.swing.*;
import java.awt.*;
import java.util.EventListener;


public class AdminDashboard extends JPanel implements EventListener {

    Main parent;

    public AdminDashboard(Main parent){
        this.parent =parent;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.CENTER;


    }


    
}
