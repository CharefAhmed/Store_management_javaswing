package Presentation;

import DAO.FournisseurDAO;
import Métier.Fournisseur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AjouterFournisseur extends JFrame {
    private JTextField txtNomFournisseur;
    private JTextField txtAdresseFournisseur;
    private JTextField txtTelephoneFournisseur;

    public AjouterFournisseur() {
        setTitle("Ajouter Fournisseur");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel lblNomFournisseur = new JLabel("Nom : ");
        txtNomFournisseur = new JTextField();
        JLabel lblAdresseFournisseur = new JLabel("Adresse : ");
        txtAdresseFournisseur = new JTextField();
        JLabel lblTelephoneFournisseur = new JLabel("Téléphone : ");
        txtTelephoneFournisseur = new JTextField();

        JButton btnAjouter = new JButton("Ajouter");
        JButton btnAnnuler = new JButton("Annuler");

        panel.add(lblNomFournisseur);
        panel.add(txtNomFournisseur);
        panel.add(lblAdresseFournisseur);
        panel.add(txtAdresseFournisseur);
        panel.add(lblTelephoneFournisseur);
        panel.add(txtTelephoneFournisseur);
        panel.add(btnAjouter);
        panel.add(btnAnnuler);

        getContentPane().add(panel);

        setLocationRelativeTo(null);
        setVisible(true);

        btnAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = txtNomFournisseur.getText();
                String adresse = txtAdresseFournisseur.getText();
                String telephone = txtTelephoneFournisseur.getText();

                Fournisseur nouveauFournisseur = new Fournisseur(nom, adresse, telephone);

                FournisseurDAO fournisseurDAO = new FournisseurDAO();
                try {
                    fournisseurDAO.insertFournisseur(nouveauFournisseur);
                    JOptionPane.showMessageDialog(AjouterFournisseur.this, "Fournisseur ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); 
                    ResponsableAchat ra =new ResponsableAchat();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(AjouterFournisseur.this, "Erreur lors de l'ajout du fournisseur.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        btnAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
					ResponsableAchat r =new ResponsableAchat();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AjouterFournisseur();
            }
        });
    }
}
