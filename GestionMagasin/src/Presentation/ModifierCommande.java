package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import DAO.CommandeDAO;
import Métier.Commande;

public class ModifierCommande extends JFrame {
    private int commandeId;
    private JTextField txtDate;
    private JTextField txtMontantTotal;

    public ModifierCommande(int commandeId) {
        this.commandeId = commandeId;

        setTitle("Modifier une commande");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2));

        JLabel lblDate = new JLabel("Date:");
        txtDate = new JTextField();

        JLabel lblMontantTotal = new JLabel("Montant Total:");
        txtMontantTotal = new JTextField();

        JButton btnValider = new JButton("Valider");
        JButton btnAnnuler = new JButton("Annuler");

        mainPanel.add(lblDate);
        mainPanel.add(txtDate);
        mainPanel.add(lblMontantTotal);
        mainPanel.add(txtMontantTotal);
        mainPanel.add(btnValider);
        mainPanel.add(btnAnnuler);

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {            
                double montantTotal = Double.parseDouble(txtMontantTotal.getText());
                try {
                    CommandeDAO commandeDAO = new CommandeDAO();
                    Commande commande = commandeDAO.getCommandeById(commandeId);
                    
                    String dateString = txtDate.getText(); 
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
                    Date newDate = null;
                    try {
                        newDate = dateFormat.parse(dateString);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
					commande.setDateCommande(newDate);
                    commande.setMontantTotal(montantTotal);
                    commandeDAO.updateCommande(commande);
                    JOptionPane.showMessageDialog(ModifierCommande.this, "Commande modifiée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    
                    ResponsableVente r=new ResponsableVente();
                    dispose(); 
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(ModifierCommande.this, "Erreur lors de la modification de la commande.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
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

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int commandeId = 1; 
                new ModifierCommande(commandeId);
            }
        });
    }
}
