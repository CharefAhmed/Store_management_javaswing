package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Login extends JFrame {
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public Login() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        lblUsername = new JLabel("CIN:");
        lblUsername.setBounds(0, 0, 166, 51);
        lblPassword = new JLabel("Password:");
        lblPassword.setBounds(0, 56, 166, 51);
        txtUsername = new JTextField(20);
        txtUsername.setBounds(171, 0, 105, 51);
        txtPassword = new JPasswordField(20);
        txtPassword.setBounds(171, 56, 105, 51);
        btnLogin = new JButton("Login");
        btnLogin.setBounds(0, 112, 166, 51);

        JPanel panel = new JPanel(); 
        panel.setLayout(null);
        panel.add(lblUsername);
        panel.add(txtUsername);
        panel.add(lblPassword);
        panel.add(txtPassword);
        panel.add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());                              
                try {
                    String userType = authenticate(username, password); 
                    switch (userType) {
                        case "RepresentantVente":
                            dispose(); 
                            new ResponsableVente(); 
                            break;
                        case "ResponsableAchat":
                            dispose();
                            new ResponsableAchat();
                            break;
                        case "Magasiner":
                            dispose();
                            new Magasiner();
                            break;
                        default:
                            JOptionPane.showMessageDialog(Login.this, "Numéro de CIN ou mot de passe incorrect.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Login.this, "Erreur lors de l'authentification.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        getContentPane().add(panel); 
        setVisible(true);
    }

    private String authenticate(String CIN, String password) throws SQLException {        
        if (CIN.equals("12345678") && password.equals("responsableV")) {
            return "RepresentantVente";
        } else if (CIN.equals("87654321") && password.equals("responsableA")) {
            return "ResponsableAchat";
        } else if (CIN.equals("74185296") && password.equals("magasiner")) {
            return "Magasiner";
        } else {
            return "Unknown";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login();
            }
        });
    }
}
