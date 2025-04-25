package Presentation;

import DAO.FournisseurDAO;
import DAO.BonAchatDAO;
import Métier.Fournisseur;
import Métier.BonAchat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AjouterBonAchat extends JFrame {
    private JComboBox<String> fournisseurComboBox;
    private JTextField IdArticleField;
    private JTextField dateAchatField;
    private JTextField montantTotalField;

    public AjouterBonAchat() {
        setTitle("Ajouter Bon d'Achat");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();

        JLabel lblFournisseurId = new JLabel("Fournisseur:");
        lblFournisseurId.setBounds(0, 1, 149, 60);
        fournisseurComboBox = new JComboBox<>();
        fournisseurComboBox.setBounds(172, 1, 149, 60);
        JLabel lblDateAchat = new JLabel("Date d'Achat (YYYY-MM-DD):");
        lblDateAchat.setBounds(0, 70, 141, 61);
        JLabel lblIdArticle = new JLabel("Article");
        lblIdArticle.setBounds(8, 198, 141, 60);
        IdArticleField = new JTextField();
        IdArticleField.setBounds(172, 199, 149, 60);
        dateAchatField = new JTextField();
        dateAchatField.setBounds(180, 76, 141, 50);
        JLabel lblMontantTotal = new JLabel("Montant Total:");
        lblMontantTotal.setBounds(0, 138, 149, 60);
        montantTotalField = new JTextField();
        montantTotalField.setBounds(172, 144, 126, 49);
        panel.setLayout(null);

        panel.add(lblFournisseurId);
        panel.add(fournisseurComboBox);
        panel.add(lblIdArticle);
        panel.add(IdArticleField);
        panel.add(lblDateAchat);
        panel.add(dateAchatField);
        panel.add(lblMontantTotal);
        panel.add(montantTotalField);

        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.setBounds(61, 287, 162, 66);
        btnAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedFournisseur = (String) fournisseurComboBox.getSelectedItem();
                    int fournisseurId = Integer.parseInt(selectedFournisseur.split(" - ")[0]);
                    int idArticle = Integer.parseInt(IdArticleField.getText());
                    String dateAchatStr = dateAchatField.getText();
                    double montantTotal = Double.parseDouble(montantTotalField.getText());                 
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    dateFormat.setLenient(false);
                    Date dateAchat = dateFormat.parse(dateAchatStr);
                    BonAchat bonAchat = new BonAchat(fournisseurId,idArticle, dateAchat, montantTotal);

                    BonAchatDAO bonAchatDAO = new BonAchatDAO();
                    bonAchatDAO.insertBonAchat(bonAchat);

                    
                    JOptionPane.showMessageDialog(AjouterBonAchat.this, "Le bon d'achat a été ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    ResponsableAchat r =new ResponsableAchat ();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AjouterBonAchat.this, "Erreur lors de l'ajout du bon d'achat. Veuillez vérifier les données saisies.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        JButton btnAnnuler = new JButton("Annuler");
        btnAnnuler.setBounds(275, 287, 162, 66);
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

        panel.add(btnAjouter);
        panel.add(btnAnnuler);

        getContentPane().add(panel);
        setLocationRelativeTo(null);
        setVisible(true);

        loadFournisseurs();
    }

    private void loadFournisseurs() {
        FournisseurDAO fournisseurDAO = new FournisseurDAO();
        try {
            List<Fournisseur> fournisseurs = fournisseurDAO.getAllFournisseurs();
            for (Fournisseur fournisseur : fournisseurs) {
                fournisseurComboBox.addItem(fournisseur.getIdFournisseur() + " - " + fournisseur.getNomFournisseur());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des fournisseurs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AjouterBonAchat();
            }
        });
    }
}
