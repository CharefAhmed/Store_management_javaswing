package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import DAO.ClientDAO;
import DAO.CommandeDAO;
import DAO.ArticleDAO;
import Métier.Client;
import Métier.Commande;
import Métier.Article;

public class AjouterCommande extends JFrame {
    private JComboBox<String> comboBoxClients;
    private JComboBox<String> comboBoxArticles;
    private JTextField txtPrixUnitaire;
    private JTextField txtQuantite;
    private JLabel lblMontantTotal;

    public AjouterCommande() {
        setTitle("Ajouter une commande");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 2));

        JLabel lblClient = new JLabel("Client:");
        comboBoxClients = new JComboBox<>();
        populateClientsComboBox();

        JLabel lblArticle = new JLabel("Article:");
        comboBoxArticles = new JComboBox<>();
        populateArticlesComboBox();

        JLabel lblPrixUnitaire = new JLabel("Prix unitaire:");
        txtPrixUnitaire = new JTextField();
        txtPrixUnitaire.setEditable(false);

        JLabel lblQuantite = new JLabel("Quantité:");
        txtQuantite = new JTextField();

        JButton btnAjouter = new JButton("Ajouter");

        lblMontantTotal = new JLabel("Montant Total:");

        JButton btnCalculerFacture = new JButton("Calculer Facture");
        JButton btnValiderCommande = new JButton("Valider");


        mainPanel.add(lblClient);
        mainPanel.add(comboBoxClients);
        mainPanel.add(lblArticle);
        mainPanel.add(comboBoxArticles);
        mainPanel.add(lblPrixUnitaire);
        mainPanel.add(txtPrixUnitaire);
        mainPanel.add(lblQuantite);
        mainPanel.add(txtQuantite);
        mainPanel.add(btnAjouter);
        mainPanel.add(lblMontantTotal);
        mainPanel.add(btnCalculerFacture);
        mainPanel.add(btnValiderCommande);


        btnAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedArticle = (String) comboBoxArticles.getSelectedItem();
                if (selectedArticle != null) {
                    try {
                        ArticleDAO articleDAO = new ArticleDAO();
                        Article article = articleDAO.getArticleByDesignation(selectedArticle);
                        if (article != null) {
                            txtPrixUnitaire.setText(String.valueOf(article.getPrixUnitaire()));
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        btnCalculerFacture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double prixUnitaire = Double.parseDouble(txtPrixUnitaire.getText());
                int quantite = Integer.parseInt(txtQuantite.getText());
                double montantTotal = prixUnitaire * quantite;
                lblMontantTotal.setText("Montant Total: " + montantTotal);
            }
        });
        btnValiderCommande.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 String selectedClient = (String) comboBoxClients.getSelectedItem();
                 String[] clientParts = selectedClient.split("-");
                 int idClient = Integer.parseInt(clientParts[0].trim());
                 String selectedArticle = (String) comboBoxArticles.getSelectedItem();
                 double prixUnitaire = Double.parseDouble(txtPrixUnitaire.getText());
                 int quantite = Integer.parseInt(txtQuantite.getText());
                 double montantTotal = prixUnitaire * quantite;
                 int qte = 0;
                 int idArticle = -1; 
                 String designation = null; 
                 try {
                     ArticleDAO articleDAO = new ArticleDAO();
                     Article article = articleDAO.getArticleByDesignation(selectedArticle);
                     if (article != null) {
                    
                    	 idArticle=article.getIdArticle();
                    	 
                    	 int qtea= articleDAO.getArticleQteByID(idArticle);
                    	 qte=qtea-quantite;
                    	 
                    	 if (qte>=0) {
                    
                         designation = article.getDesignation(); 
                         article.setQuantite(qte);
                         if (qte==0) {
                        	 article.setEtatArt("hors stock");
                        	 
                         }
                         articleDAO.updateArticle(article);
                         Date dateCommande = new Date();
                         Commande nouvelleCommande = new Commande(idClient,idArticle,selectedArticle,quantite, dateCommande, montantTotal);

                         try {
                             CommandeDAO commandeDAO = new CommandeDAO();                    
                             commandeDAO.insertCommande(nouvelleCommande);
               
                             JOptionPane.showMessageDialog(AjouterCommande.this, "Commande ajoutée avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                         } catch (SQLException ex) {
                             JOptionPane.showMessageDialog(AjouterCommande.this, "Erreur lors de l'ajout de la commande.", "Erreur", JOptionPane.ERROR_MESSAGE);
                             ex.printStackTrace();
                         }

                        }
                    	 else JOptionPane.showMessageDialog(AjouterCommande.this, "Quantite en stock = "+articleDAO.getArticleQteByID(idArticle), "Erreur", JOptionPane.ERROR_MESSAGE);
       
                  }
                 }
                 catch (SQLException ex) {
                     ex.printStackTrace();
                 }              
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

    private void populateClientsComboBox() {
        try {
            ClientDAO clientDAO = new ClientDAO();
            List<Client> clients = clientDAO.getAllClients();
            for (Client client : clients) {
                comboBoxClients.addItem(client.getIdClient() + " - " + client.getNomClient());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void populateArticlesComboBox() {
        try {
            ArticleDAO articleDAO = new ArticleDAO();
            List<Article> articles = articleDAO.getAllArticlesEnStock();
            for (Article article : articles) {
                comboBoxArticles.addItem(article.getDesignation());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AjouterCommande();
            }
        });
    }
}
