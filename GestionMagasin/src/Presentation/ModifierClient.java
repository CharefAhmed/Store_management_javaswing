package Presentation;

import DAO.ClientDAO;
import Métier.Client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ModifierClient extends JFrame {
    private JTextField txtNom;
    private JTextField txtAdresse;
    private JTextField txtTelephone;

    public ModifierClient(int clientId) {
        setTitle("Modifier Client");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel lblNom = new JLabel("Nom:");
        JLabel lblAdresse = new JLabel("Adresse:");
        JLabel lblTelephone = new JLabel("Téléphone:");

        txtNom = new JTextField(20);
        txtAdresse = new JTextField(20);
        txtTelephone = new JTextField(20);

        JButton btnModifier = new JButton("Modifier");
        JButton btnAnnuler = new JButton("Annuler");

        panel.add(lblNom);
        panel.add(txtNom);
        panel.add(lblAdresse);
        panel.add(txtAdresse);
        panel.add(lblTelephone);
        panel.add(txtTelephone);
        panel.add(btnModifier);
        panel.add(btnAnnuler);

        add(panel);

        try {
            ClientDAO clientDAO = new ClientDAO();
            Client client = clientDAO.getClientById(clientId);
            if (client != null) {
                txtNom.setText(client.getNomClient());
                txtAdresse.setText(client.getAdresseClient());
                txtTelephone.setText(client.getTelephoneClient());
            } else {
                JOptionPane.showMessageDialog(this, "Client non trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                dispose(); 
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération du client.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        btnModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = txtNom.getText();
                String adresse = txtAdresse.getText();
                String telephone = txtTelephone.getText();

                try {
                    ClientDAO clientDAO = new ClientDAO();
                    Client client = new Client(clientId, nom, adresse, telephone);
                    clientDAO.updateClient(client);
                    JOptionPane.showMessageDialog(ModifierClient.this, "Client modifié avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    ResponsableVente r=new ResponsableVente();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(ModifierClient.this, "Erreur lors de la modification du client.", "Erreur", JOptionPane.ERROR_MESSAGE);
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

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ModifierClient(1); 
            }
        });
    }
}
