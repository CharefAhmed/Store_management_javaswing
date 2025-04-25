package Presentation;

import DAO.ClientDAO;
import Métier.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class AjouterClient extends JFrame {
    private JLabel lblNom;
    private JTextField txtNom;
    private JLabel lblAdresse;
    private JTextField txtAdresse;
    private JLabel lblTelephone;
    private JTextField txtTelephone;
    private JButton btnAjouter;
    private JButton btnAnnuler;

    public AjouterClient() {
        setTitle("Ajouter un client");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        lblNom = new JLabel("Nom:");
        txtNom = new JTextField();

        lblAdresse = new JLabel("Adresse:");
        txtAdresse = new JTextField();

        lblTelephone = new JLabel("Téléphone:");
        txtTelephone = new JTextField();

        btnAjouter = new JButton("Ajouter");
        btnAnnuler = new JButton("Annuler");

        panel.add(lblNom);
        panel.add(txtNom);
        panel.add(lblAdresse);
        panel.add(txtAdresse);
        panel.add(lblTelephone);
        panel.add(txtTelephone);
        panel.add(btnAjouter);
        panel.add(btnAnnuler);

        getContentPane().add(panel);

        setLocationRelativeTo(null);

        btnAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = txtNom.getText();
                String adresse = txtAdresse.getText();
                String telephone = txtTelephone.getText();
                if (nom.isEmpty() || adresse.isEmpty() || telephone.isEmpty()) {
                    JOptionPane.showMessageDialog(AjouterClient.this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    Client client = new Client(0, nom, adresse, telephone);
                    try {
                        ClientDAO cl = new ClientDAO();
						cl.insertClient(client);
                        JOptionPane.showMessageDialog(AjouterClient.this, "Client ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        ResponsableVente responsableVente = new ResponsableVente();
						
                        dispose();
                        ResponsableVente r=new ResponsableVente();
                        
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(AjouterClient.this, "Erreur lors de l'ajout du client.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });

        btnAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
                try {
                    ResponsableVente r = new ResponsableVente();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }                 
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AjouterClient().setVisible(true);
            }
        });
    }
}
