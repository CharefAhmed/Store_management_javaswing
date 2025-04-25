package Presentation;

import DAO.FournisseurDAO;
import Métier.Fournisseur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ModifierFournisseur extends JFrame {
    private JTextField txtNom;
    private JTextField txtAdresse;
    private JTextField txtTelephone;

    public ModifierFournisseur(int fournisseurId) {
        setTitle("Modifier Fournisseur");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel lblNom = new JLabel("Nom:");
        txtNom = new JTextField(20);
        JLabel lblAdresse = new JLabel("Adresse:");
        txtAdresse = new JTextField(20);
        JLabel lblTelephone = new JLabel("Téléphone:");
        txtTelephone = new JTextField(20);

        panel.add(lblNom);
        panel.add(txtNom);
        panel.add(lblAdresse);
        panel.add(txtAdresse);
        panel.add(lblTelephone);
        panel.add(txtTelephone);

        JButton btnModifier = new JButton("Modifier");
        JButton btnAnnuler = new JButton("Annuler");

        panel.add(btnModifier);
        panel.add(btnAnnuler);

        add(panel);
        FournisseurDAO fournisseurDAO = new FournisseurDAO();
        try {
            Fournisseur fournisseur = fournisseurDAO.getFournisseurById(fournisseurId);
            txtNom.setText(fournisseur.getNomFournisseur());
            txtAdresse.setText(fournisseur.getAdresseFournisseur());
            txtTelephone.setText(fournisseur.getTelephoneFournisseur());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des détails du fournisseur.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        btnModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = txtNom.getText().trim();
                String adresse = txtAdresse.getText().trim();
                String telephone = txtTelephone.getText().trim();
                if (nom.isEmpty() || adresse.isEmpty() || telephone.isEmpty()) {
                    JOptionPane.showMessageDialog(ModifierFournisseur.this, "Veuillez remplir tous les champs.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                FournisseurDAO fournisseurDAO = new FournisseurDAO();
                try {
                    fournisseurDAO.updateFournisseur(fournisseurId, nom, adresse, telephone);
                    JOptionPane.showMessageDialog(ModifierFournisseur.this, "Fournisseur modifié avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    ResponsableAchat ra = new ResponsableAchat();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(ModifierFournisseur.this, "Erreur lors de la modification du fournisseur.", "Erreur", JOptionPane.ERROR_MESSAGE);
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
                new ModifierFournisseur(1).setVisible(true);
            }
        });
    }
}

