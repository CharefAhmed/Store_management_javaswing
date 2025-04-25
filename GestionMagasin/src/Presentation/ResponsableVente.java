package Presentation;

import DAO.ClientDAO;
import DAO.CommandeDAO;
import DAO.ArticleDAO;
import Métier.Client;
import Métier.Commande;
import Métier.Article;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.sql.SQLException;

public class ResponsableVente extends JFrame {
    private JTabbedPane tabbedPane;
	private Container clientsPanel;

    public ResponsableVente() throws SQLException {
        setTitle("Responsable Vente");
        setSize(1186, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        JPanel clientsPanel = new JPanel();
        clientsPanel.setLayout(new BorderLayout());
        // Boutons pour gérer les clients
        JButton btnAjouterClient = new JButton("Ajouter Client");
        JButton btnModifierClient = new JButton("Modifier Client");
        JButton btnSupprimerClient = new JButton("Supprimer Client");

        JPanel clientsButtonPanel = new JPanel();
        clientsButtonPanel.add(btnAjouterClient);
        clientsButtonPanel.add(btnModifierClient);
        clientsButtonPanel.add(btnSupprimerClient);

        clientsPanel.add(clientsButtonPanel, BorderLayout.SOUTH);


        // Affichage pour les clients
      
        JTable clientsTable = new JTable();
        JScrollPane clientsScrollPane = new JScrollPane(clientsTable);
        clientsPanel.add(clientsScrollPane, BorderLayout.CENTER);

        DefaultTableModel clientsTableModel = new DefaultTableModel();
        clientsTableModel.addColumn("ID");
        clientsTableModel.addColumn("Nom");
        clientsTableModel.addColumn("Adresse");
        clientsTableModel.addColumn("Téléphone");
        clientsTable.setModel(clientsTableModel);

        ClientDAO cl=new ClientDAO();
        List<Client> clients = new ArrayList(); 
        clients = cl.getAllClients();
        for (Client client : clients) {
            clientsTableModel.addRow(new Object[]{client.getIdClient(), client.getNomClient(), client.getAdresseClient(), client.getTelephoneClient()});
        }

        tabbedPane.addTab("Clients", clientsPanel);
        this.clientsPanel = clientsPanel;

        // Affichage des commandes
        JPanel commandesPanel = new JPanel();
        commandesPanel.setLayout(new BorderLayout());
        JTable commandesTable = new JTable();
        JScrollPane commandesScrollPane = new JScrollPane(commandesTable);
        commandesPanel.add(commandesScrollPane, BorderLayout.CENTER);

        DefaultTableModel commandesTableModel = new DefaultTableModel();
        commandesTableModel.addColumn("ID");
        commandesTableModel.addColumn("ID Client");
        commandesTableModel.addColumn("ID Article"); 
        commandesTableModel.addColumn("Désignation Article"); 
        commandesTableModel.addColumn("Quantité Article");
        commandesTableModel.addColumn("Date");
        commandesTableModel.addColumn("Montant Total");
        commandesTable.setModel(commandesTableModel);

        CommandeDAO cmd=new CommandeDAO();
        List<Commande> commandes = new ArrayList();
        commandes = cmd.getAllCommandes();
        for (Commande commande : commandes) {
            commandesTableModel.addRow(new Object[]{commande.getIdCommande(), commande.getIdClient(),commande.getIdArticle(),commande.getDesignation(),commande.getQuantite(),commande.getDateCommande(), commande.getMontantTotal()});
        }

        tabbedPane.addTab("Commandes", commandesPanel);

        // Affichage des articles
        JPanel articlesPanel = new JPanel();
        articlesPanel.setLayout(new BorderLayout());
        JTable articlesTable = new JTable();
        JScrollPane articlesScrollPane = new JScrollPane(articlesTable);
        articlesPanel.add(articlesScrollPane, BorderLayout.CENTER);

        DefaultTableModel articlesTableModel = new DefaultTableModel();
        articlesTableModel.addColumn("ID");
        articlesTableModel.addColumn("Désignation");
        articlesTableModel.addColumn("Catégorie");
        articlesTableModel.addColumn("Quantité");
        articlesTableModel.addColumn("Prix Unitaire");
        articlesTableModel.addColumn("Etat Article");
        
        articlesTable.setModel(articlesTableModel);

        ArticleDAO ar= new ArticleDAO(); 
        List<Article> articles = new ArrayList() ;
        articles=ar.getAllArticlesEnStock();
        for (Article article : articles) {
            articlesTableModel.addRow(new Object[]{article.getIdArticle(), article.getDesignation(), article.getCategorie(), article.getQuantite(), article.getPrixUnitaire(),article.getEtatArt()});
        }

        tabbedPane.addTab("Articles", articlesPanel);

        getContentPane().add(tabbedPane);

        setLocationRelativeTo(null);
        setVisible(true);
        JTextField txtRechercheDesignation = new JTextField(20);
        JTextField txtRechercheCategorie = new JTextField(20);

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Recherche par Désignation:"));
        searchPanel.add(txtRechercheDesignation);
        JButton btnRechercheDesignation=new JButton("Recherche Désignation");
		searchPanel.add(btnRechercheDesignation);
        searchPanel.add(new JLabel("Recherche par Catégorie:"));
        searchPanel.add(txtRechercheCategorie);
        JButton btnRechercheCategorie=new JButton("Recherche Catégorie");
		searchPanel.add(btnRechercheCategorie);
        articlesPanel.add(searchPanel, BorderLayout.PAGE_END);

        btnRechercheDesignation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String designation = txtRechercheDesignation.getText(); 
                try {
                    ArticleDAO articleDAO = new ArticleDAO();
                    List<Article> articles = articleDAO.getArticlesByDesignation(designation);
                    DefaultTableModel model = (DefaultTableModel) articlesTable.getModel();
                    model.setRowCount(0); 
                    for (Article article : articles) {
                        model.addRow(new Object[]{article.getIdArticle(), article.getDesignation(), article.getCategorie(), article.getQuantite(), article.getPrixUnitaire(), article.getEtatArt()});
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ResponsableVente.this, "Erreur lors de la recherche par désignation.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnRechercheCategorie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categorie = txtRechercheCategorie.getText(); 
                try {
                    ArticleDAO articleDAO = new ArticleDAO();
                    List<Article> articles = articleDAO.getArticlesByCategory(categorie);
                    DefaultTableModel model = (DefaultTableModel) articlesTable.getModel();
                    model.setRowCount(0); 
                    for (Article article : articles) {
                        model.addRow(new Object[]{article.getIdArticle(), article.getDesignation(), article.getCategorie(), article.getQuantite(), article.getPrixUnitaire(), article.getEtatArt()});
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ResponsableVente.this, "Erreur lors de la recherche par catégorie.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        btnAjouterClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                AjouterClient ajouterClient = new AjouterClient();
                ajouterClient.setVisible(true);
                dispose();

            }
        });
        btnModifierClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = clientsTable.getSelectedRow();
                if (selectedRowIndex != -1) { 
                    int clientId = (int) clientsTable.getValueAt(selectedRowIndex, 0); 
                    ModifierClient modifierClient = new ModifierClient(clientId);
                    modifierClient.setVisible(true);
                    modifierClient.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            dispose();
                            
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(ResponsableVente.this, "Veuillez sélectionner un client à modifier.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnSupprimerClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = clientsTable.getSelectedRow();
                if (selectedRowIndex != -1) { 
                    int clientId = (int) clientsTable.getValueAt(selectedRowIndex, 0); 
                    try {
                        ClientDAO clientDAO = new ClientDAO();
                        Client client = clientDAO.getClientById(clientId);
                        int option = JOptionPane.showConfirmDialog(ResponsableVente.this, "Êtes-vous sûr de vouloir supprimer ce client ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            clientDAO.deleteClient(client);
                            dispose();
                            ResponsableVente re=new ResponsableVente();
                            }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(ResponsableVente.this, "Erreur lors de la suppression du client.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                    
                } else {
                    JOptionPane.showMessageDialog(ResponsableVente.this, "Veuillez sélectionner un client à supprimer.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

     
        JButton btnAjouterCommande = new JButton("Ajouter Commande");
        JButton btnModifierCommande = new JButton("Modifier Commande");
        JButton btnSupprimerCommande = new JButton("Supprimer Commande");
        JPanel commandesButtonPanel = new JPanel();
        commandesButtonPanel.add(btnAjouterCommande);
        commandesButtonPanel.add(btnModifierCommande);
        commandesButtonPanel.add(btnSupprimerCommande);

        commandesPanel.add(commandesButtonPanel, BorderLayout.SOUTH);

        btnAjouterCommande.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {   
                AjouterCommande ajouterCommande = new AjouterCommande();
                ajouterCommande.setVisible(true);
                dispose();
            }
        });

        btnModifierCommande.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = commandesTable.getSelectedRow();
                if (selectedRowIndex != -1) { 
                    int commandeId = (int) commandesTable.getValueAt(selectedRowIndex, 0); 
                    ModifierCommande modifierCommande = new ModifierCommande(commandeId);
                    modifierCommande.setVisible(true);
                    dispose(); 

                    modifierCommande.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            updateCommandesTable();
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(ResponsableVente.this, "Veuillez sélectionner une commande à modifier.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnSupprimerCommande.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = commandesTable.getSelectedRow();
                if (selectedRowIndex != -1) { 
                    int commandeId = (int) commandesTable.getValueAt(selectedRowIndex, 0); 
                    try {
                        CommandeDAO commandeDAO = new CommandeDAO();
                        Commande commande = commandeDAO.getCommandeById(commandeId);
                        int option = JOptionPane.showConfirmDialog(ResponsableVente.this, "Êtes-vous sûr de vouloir supprimer cette commande ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                        	commandeDAO.deleteCommande(commandeId);
                        	  dispose();
                              ResponsableVente r=new ResponsableVente(); 
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(ResponsableVente.this, "Erreur lors de la suppression de la commande.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(ResponsableVente.this, "Veuillez sélectionner une commande à supprimer.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        //Bouton Refresh
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		try {
        			ResponsableVente r=new ResponsableVente();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
        	}
        });
        btnRefresh.setBounds(1405, 431, 85, 21);
        searchPanel.add(btnRefresh);
    }

    public  void updateClientsTable() {
        try {
        	JTable clientsTable = new JTable();
            JScrollPane clientsScrollPane = new JScrollPane(clientsTable);
            clientsPanel.add(clientsScrollPane, BorderLayout.CENTER);
            DefaultTableModel clientsTableModel = (DefaultTableModel) clientsTable.getModel();
            clientsTableModel.setRowCount(0); 

            ClientDAO clientDAO = new ClientDAO();
            List<Client> updatedClients = clientDAO.getAllClients();

            for (Client client : updatedClients) {
                clientsTableModel.addRow(new Object[]{client.getIdClient(), client.getNomClient(), client.getAdresseClient(), client.getTelephoneClient()});
                
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(ResponsableVente.this, "Erreur lors de la mise à jour du tableau des clients.", "Erreur", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    public void updateCommandesTable() {
        try {
        	JTable commandesTable = new JTable();
            JScrollPane commandesScrollPane = new JScrollPane(commandesTable);

            DefaultTableModel commandesTableModel = (DefaultTableModel) commandesTable.getModel();
            commandesTableModel.setRowCount(0); 

            CommandeDAO commandeDAO = new CommandeDAO();
            List<Commande> updatedCommandes = commandeDAO.getAllCommandes();
            for (Commande commande : updatedCommandes) {
                commandesTableModel.addRow(new Object[]{commande.getIdCommande(), commande.getIdClient(), commande.getDateCommande(), commande.getMontantTotal()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour du tableau des commandes.", "Erreur", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
     
    }


    
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
                try {
					new ResponsableVente();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
        });
    }
}
