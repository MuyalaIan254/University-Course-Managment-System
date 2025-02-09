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
    private Image backgroundImage;


  public Login(Main parent) {
        this.parent = parent;

       
        backgroundImage = new ImageIcon("app/src/main/java/universitycoursemanagementsystem/images/login.jpg").getImage();
        
       
       
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

    
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0,getWidth(),getHeight(), this);
    }

    public void actionPerformed(ActionEvent e) {
        String user = username.getText();
        String pass = new String(password.getPassword());

        if (user.equals("admin") && pass.equals("admin")) {
            // Transition to a main system screen 
            parent.showCard("AdminDashboard");
        } else {
            JOptionPane.showMessageDialog(this, "Log In Failed");
        }
    }
}

