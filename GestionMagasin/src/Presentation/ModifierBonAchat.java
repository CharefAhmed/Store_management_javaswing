package Presentation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import DAO.BonAchatDAO;
import DAO.FournisseurDAO; // Importez votre DAO pour les fournisseurs
import Métier.BonAchat;
import Métier.Fournisseur;

public class ModifierBonAchat extends JFrame {
    private int bonAchatId;
    private JComboBox<String> fournisseurComboBox; 
    private JTextField dateAchatField;
    private JTextField montantTotalField;

    public ModifierBonAchat(int bonAchatId) {
        this.bonAchatId = bonAchatId;

        setTitle("Modifier Bon d'Achat");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel lblFournisseur = new JLabel("Fournisseur:");   
        try {
            FournisseurDAO fournisseurDAO = new FournisseurDAO();
            fournisseurComboBox = new JComboBox<>();
            List<Fournisseur> fournisseurs = fournisseurDAO.getAllFournisseurs();
            for (Fournisseur fournisseur : fournisseurs) {
                fournisseurComboBox.addItem(fournisseur.getIdFournisseur() + " - " + fournisseur.getNomFournisseur());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        

        JLabel lblDateAchat = new JLabel("Date d'Achat (YYYY-MM-DD):");
        dateAchatField = new JTextField();
        JLabel lblMontantTotal = new JLabel("Montant Total:");
        montantTotalField = new JTextField();
        panel.add(lblDateAchat);
        panel.add(dateAchatField);
        panel.add(lblMontantTotal);
        panel.add(montantTotalField);

        BonAchatDAO bonAchatDAO = new BonAchatDAO();
        try {
            BonAchat bonAchat = bonAchatDAO.getBonAchatById(bonAchatId);
            FournisseurDAO fournisseurDAO=new FournisseurDAO();
            Fournisseur fournisseur = fournisseurDAO.getFournisseurById(bonAchat.getIdFournisseur());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = dateFormat.format(bonAchat.getDateAchat());
			dateAchatField.setText(dateStr);
			montantTotalField.setText(String.valueOf(bonAchat.getMontantTotal()));
        } catch (SQLException ex) {
            dispose();
        }

        JButton btnModifier = new JButton("Modifier");
        btnModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedFournisseur = (String) fournisseurComboBox.getSelectedItem();
                    int fournisseurId = Integer.parseInt(selectedFournisseur.split(" - ")[0]);

                    String dateAchatStr = dateAchatField.getText();
                    double montantTotal = Double.parseDouble(montantTotalField.getText());
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    dateFormat.setLenient(false);
                    Date dateAchat = dateFormat.parse(dateAchatStr);
                    BonAchat bonAchat = new BonAchat(bonAchatId,dateAchat, montantTotal);
                    BonAchatDAO bonAchatDAO = new BonAchatDAO();
                    bonAchatDAO.updateBonAchat(bonAchat);
                    JOptionPane.showMessageDialog(ModifierBonAchat.this, "Le bon d'achat a été modifié avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    try {
    					ResponsableAchat r =new ResponsableAchat();
    				} catch (SQLException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ModifierBonAchat.this, "Erreur lors de la modification du bon d'achat. Veuillez vérifier les données saisies.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        JButton btnAnnuler = new JButton("Annuler");
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

        panel.add(btnModifier);
        panel.add(btnAnnuler);
        getContentPane().add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ModifierBonAchat(1); 
            }
        });
    }
}
