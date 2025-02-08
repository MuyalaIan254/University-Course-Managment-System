package universitycoursemanagementsystem.authentication;

import universitycoursemanagementsystem.Main;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JPanel implements ActionListener {
       
    JTextField firstName, lastName, email, phone;
    JPasswordField password, confirmPassword;
    JCheckBox showPassword, showConfirmPassword, checkMale, checkFemale;
    JLabel userLabel1, userLabel2, emailLabel, phoneLabel, passLabel1, passLabel2, genderLabel;
    JButton register, logIn;
    Main parent;

    public Register(Main parent) {
        this.parent = parent;

        firstName = new JTextField(15);
        lastName = new JTextField(15);
        email = new JTextField(15);
        phone = new JTextField(15);
        password = new JPasswordField(15);
        confirmPassword = new JPasswordField(15);
        checkMale = new JCheckBox("M");
        checkFemale = new JCheckBox("F");
        showPassword = new JCheckBox();
        showConfirmPassword = new JCheckBox();

        userLabel1 = new JLabel("First name");
        userLabel2 = new JLabel("Last Name");
        genderLabel = new JLabel("Gender");
        emailLabel = new JLabel("Email");
        phoneLabel = new JLabel("Phone");
        passLabel1 = new JLabel("Enter your password");
        passLabel2 = new JLabel("Confirm your password");
        register = new JButton("Register");
        logIn = new JButton("Log In");

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(userLabel1, gbc);
        gbc.gridx = 1;
        add(firstName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(userLabel2, gbc);
        gbc.gridx = 1;
        add(lastName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(genderLabel, gbc);
        gbc.gridx = 1;
        add(checkMale, gbc);
        gbc.gridx = 2;
        add(checkFemale, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(emailLabel, gbc);
        gbc.gridx = 1;
        add(email, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(phoneLabel, gbc);
        gbc.gridx = 1;
        add(phone, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(passLabel1, gbc);
        gbc.gridx = 1;
        add(password, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(passLabel2, gbc);
        gbc.gridx = 1;
        add(confirmPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(register, gbc);
        gbc.gridx = 1;
        add(logIn, gbc);

        logIn.addActionListener(e -> parent.showCard("LogIn"));
        register.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        String fname = firstName.getText();
        String lname = lastName.getText();
        String mail = email.getText();
        String phoneNo = phone.getText();
        String pass = new String(password.getPassword());
        String confirmPass = new String(confirmPassword.getPassword());

        if (fname.isEmpty() || lname.isEmpty() || mail.isEmpty() || phoneNo.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled");
        } else if (!pass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match");
        } else if (!checkMale.isSelected() && !checkFemale.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select a gender");
        } else if (checkMale.isSelected() && checkFemale.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select one gender");
        } else {
            JOptionPane.showMessageDialog(this, "Registration Successful");
            parent.showCard("AdminDashboard");
        }
    }
}

