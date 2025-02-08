package universitycoursemanagementsystem.authentication;

import universitycoursemanagementsystem.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JPanel implements ActionListener {
    JTextField username;
    JPasswordField password;
    JLabel userLabel, passLabel;
    JButton logIn, register;
    Main parent;

  public Login(Main parent) {
        this.parent = parent;
       
        username = new JTextField(15);
        password = new JPasswordField(15);
        userLabel = new JLabel("Username");
        passLabel = new JLabel("Password");
        logIn = new JButton("Log In");
        register = new JButton("Register");

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(userLabel, gbc);
        gbc.gridx = 1;
        add(username, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passLabel, gbc);
        gbc.gridx = 1;
        add(password, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(logIn, gbc);
        gbc.gridx = 1;
        add(register, gbc);

        



        logIn.addActionListener(this);
        register.addActionListener(e -> parent.showCard("Register"));
    }

    public void actionPerformed(ActionEvent e) {
        String user = username.getText();
        String pass = new String(password.getPassword());

        if (user.equals("admin") && pass.equals("admin")) {
            JOptionPane.showMessageDialog(this, "Log In Successful");
            // Transition to a main system screen (e.g., dashboard)
            parent.showCard("AdminDashboard");
        } else {
            JOptionPane.showMessageDialog(this, "Log In Failed");
        }
    }
}
    
