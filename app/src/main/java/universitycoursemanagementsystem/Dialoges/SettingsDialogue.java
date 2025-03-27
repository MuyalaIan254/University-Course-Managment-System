
package universitycoursemanagementsystem.Dialoges;

import java.awt.CardLayout;

import javax.swing.JOptionPane;

import universitycoursemanagementsystem.Database.PersonDAO;

public class SettingsDialogue extends javax.swing.JDialog {

    private CardLayout cardLayout;
    

    public SettingsDialogue(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        cardLayout = (CardLayout) actionsPanel.getLayout();
    }

    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        addUserButton = new javax.swing.JButton();
        ChangePasswordButton = new javax.swing.JButton();
        actionsPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        addUserPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        username1 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        password1 = new javax.swing.JPasswordField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        password2 = new javax.swing.JPasswordField();
        jSeparator3 = new javax.swing.JSeparator();
        addButton = new javax.swing.JButton();
        resetPasswordPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        username2 = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        oldPassword = new javax.swing.JPasswordField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        newPassword = new javax.swing.JPasswordField();
        jSeparator6 = new javax.swing.JSeparator();
        confirmButton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        confirmNewPassword = new javax.swing.JPasswordField();
        jSeparator7 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        addUserButton.setText("Add User");
        addUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserButtonActionPerformed(evt);
            }
        });

        ChangePasswordButton.setText("Change Password");
        ChangePasswordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangePasswordButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ChangePasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(addUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(ChangePasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        actionsPanel.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 403, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 406, Short.MAX_VALUE)
        );

        actionsPanel.add(jPanel1, "card2");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-user-30.png"))); // NOI18N

        username1.setEditable(true);
        username1.setText("Username");
        username1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        username1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                username1FocusGained(evt);
            }
        });

        jLabel2.setText("Enter Password      :");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-password-30.png"))); // NOI18N

        password1.setBackground(new java.awt.Color(60, 63, 65));
        password1.setText("jPasswordField11234567890");
        password1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        password1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                password1FocusGained(evt);
            }
        });

        jSeparator2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("Confirm Password  :");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-password-30.png"))); // NOI18N

        password2.setBackground(new java.awt.Color(60, 63, 65));
        password2.setText("jPasswordField21234567890");
        password2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        password2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                password2FocusGained(evt);
            }
        });
       

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addUserPanelLayout = new javax.swing.GroupLayout(addUserPanel);
        addUserPanel.setLayout(addUserPanelLayout);
        addUserPanelLayout.setHorizontalGroup(
            addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addUserPanelLayout.createSequentialGroup()
                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addUserPanelLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(addUserPanelLayout.createSequentialGroup()
                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jSeparator2)
                                        .addComponent(jSeparator1)
                                        .addComponent(username1)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(password1, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(addUserPanelLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator3)
                                    .addComponent(password2)))))
                    .addGroup(addUserPanelLayout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        addUserPanelLayout.setVerticalGroup(
            addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addUserPanelLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(username1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(password1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(password2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addButton)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        actionsPanel.add(addUserPanel, "addUserContent");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-user-30.png"))); // NOI18N

        username2.setEditable(true);
        username2.setText("Username");
        username2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        username2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                username2FocusGained(evt);
            }
        });

        jLabel7.setText("Enter Old Password         :");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-password-30.png"))); // NOI18N

        oldPassword.setBackground(new java.awt.Color(60, 63, 65));
        oldPassword.setText("jPasswordField11234567890");
        oldPassword.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        oldPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                oldPasswordFocusGained(evt);
            }
        });

        jLabel9.setText("Enter New Password        :");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-password-30.png"))); // NOI18N

        newPassword.setBackground(new java.awt.Color(60, 63, 65));
        newPassword.setText("jPasswordField21234567890");
        newPassword.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        newPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                newPasswordFocusGained(evt);
            }
        });
       

        confirmButton.setText("Confirm");
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });

        jLabel11.setText("Confirm New Password    :");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-password-30.png"))); // NOI18N

        confirmNewPassword.setText("jPasswordField11234567890");
        confirmNewPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                confirmNewPasswordFocusGained(evt);
            }
        });

        javax.swing.GroupLayout resetPasswordPanelLayout = new javax.swing.GroupLayout(resetPasswordPanel);
        resetPasswordPanel.setLayout(resetPasswordPanelLayout);
        resetPasswordPanelLayout.setHorizontalGroup(
            resetPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resetPasswordPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(resetPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(resetPasswordPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(resetPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(resetPasswordPanelLayout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(resetPasswordPanelLayout.createSequentialGroup()
                                .addGroup(resetPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator4)
                                    .addComponent(username2, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
                                .addGap(39, 39, 39))))
                    .addGroup(resetPasswordPanelLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(resetPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(resetPasswordPanelLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(resetPasswordPanelLayout.createSequentialGroup()
                                .addGroup(resetPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator6)
                                    .addComponent(jSeparator5)
                                    .addGroup(resetPasswordPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(39, 39, 39))))
                    .addGroup(resetPasswordPanelLayout.createSequentialGroup()
                        .addGroup(resetPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(resetPasswordPanelLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(oldPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
                            .addGroup(resetPasswordPanelLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(newPassword))
                            .addGroup(resetPasswordPanelLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(resetPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(confirmNewPassword)
                                    .addComponent(jSeparator7))))
                        .addGap(39, 39, 39))))
            .addGroup(resetPasswordPanelLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        resetPasswordPanelLayout.setVerticalGroup(
            resetPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resetPasswordPanelLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(resetPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(username2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(resetPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(oldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(resetPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(newPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addGap(5, 5, 5)
                .addGroup(resetPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(confirmNewPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(confirmButton)
                .addGap(94, 94, 94))
        );

        actionsPanel.add(resetPasswordPanel, "resetPanel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(actionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(actionsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserButtonActionPerformed
        cardLayout.show(actionsPanel,"addUserContent");
        setTextField();
    }//GEN-LAST:event_addUserButtonActionPerformed


    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
        String username = username2.getText();
        String oldPass = new String(oldPassword.getPassword());
        String newPass = new String(newPassword.getPassword());
        String confirmPass = new String(confirmNewPassword.getPassword());

        if(username.isEmpty()||oldPass.isEmpty()||newPass.isEmpty()||confirmPass.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please fill all the fields");
            username2.setText("Username");
            oldPassword.setText("jPasswordField11234567890");
            newPassword.setText("jPasswordField21234567890");
            confirmNewPassword.setText("jPasswordField11234567890");
        }else{
            PersonDAO personDAO = new PersonDAO();
           if(newPass.equals(confirmPass)){
               personDAO.changePassword(username, oldPass, newPass);
               
           }else{
               JOptionPane.showMessageDialog(this, "Passwords do not match");
           }
        }
       
    }//GEN-LAST:event_confirmButtonActionPerformed

    private void ChangePasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangePasswordButtonActionPerformed
        cardLayout.show(actionsPanel,"resetPanel");
        setTextField();
    }//GEN-LAST:event_ChangePasswordButtonActionPerformed

    private void setTextField(){
        username1.setText("Username");
        password1.setText("jPasswordField11234567890");
        password2.setText("jPasswordField21234567890");
        username2.setText("Username");
        oldPassword.setText("jPasswordField11234567890");
        newPassword.setText("jPasswordField21234567890");
        confirmNewPassword.setText("jPasswordField11234567890");
    }


    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
            String username = username1.getText();
            String pass1 = new String(password1.getPassword());
            String pass2 = new String(password2.getPassword());
        
            if(username.isEmpty()||pass1.isEmpty()||pass2.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please fill all the fields");
                username1.setText("Username");
                password1.setText("jPasswordField11234567890");
                password2.setText("jPasswordField21234567890");
            }else{
            
                if(pass1.equals(pass2)){
                    PersonDAO personDAO = new PersonDAO();
                    personDAO.createUser(username, pass2);
                    
                }else{
                    JOptionPane.showMessageDialog(this, "Passwords do not match");
                }
            }
    }//GEN-LAST:event_addButtonActionPerformed

    private void username1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_username1FocusGained
        username1.setText("");
    }//GEN-LAST:event_username1FocusGained

    private void password1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_password1FocusGained
        password1.setText("");
    }//GEN-LAST:event_password1FocusGained

    private void password2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_password2FocusGained
        password2.setText("");
    }//GEN-LAST:event_password2FocusGained

    private void username2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_username2FocusGained
         username2.setText("");
    }//GEN-LAST:event_username2FocusGained

    private void oldPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_oldPasswordFocusGained
         oldPassword.setText("");
    }//GEN-LAST:event_oldPasswordFocusGained

    private void newPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_newPasswordFocusGained
        newPassword.setText("");
    }//GEN-LAST:event_newPasswordFocusGained

    private void confirmNewPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_confirmNewPasswordFocusGained
        confirmNewPassword.setText("");
    }//GEN-LAST:event_confirmNewPasswordFocusGained

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ChangePasswordButton;
    private javax.swing.JPanel actionsPanel;
    private javax.swing.JButton addButton;
    private javax.swing.JButton addUserButton;
    private javax.swing.JPanel addUserPanel;
    private javax.swing.JButton confirmButton;
    private javax.swing.JPasswordField confirmNewPassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPasswordField newPassword;
    private javax.swing.JPasswordField oldPassword;
    private javax.swing.JPasswordField password1;
    private javax.swing.JPasswordField password2;
    private javax.swing.JPanel resetPasswordPanel;
    private javax.swing.JTextField username1;
    private javax.swing.JTextField username2;
    // End of variables declaration//GEN-END:variables
}
