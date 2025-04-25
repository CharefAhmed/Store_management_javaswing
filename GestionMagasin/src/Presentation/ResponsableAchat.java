package Presentation;

import DAO.FournisseurDAO;
import DAO.BonAchatDAO;
import DAO.ArticleDAO;
import Métier.Fournisseur;
import Métier.BonAchat;
import Métier.Article;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class ResponsableAchat extends JFrame {
    private JTabbedPane tabbedPane;
    private Container fournisseursPanel;

    public ResponsableAchat() throws SQLException {
        setTitle("Responsable d'Achat");
        setSize(1186, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        JPanel fournisseursPanel = new JPanel();
        fournisseursPanel.setLayout(new BorderLayout());
        JButton btnAjouterFournisseur = new JButton("Ajouter Fournisseur");
        JButton btnModifierFournisseur = new JButton("Modifier Fournisseur");
        JButton btnSupprimerFournisseur = new JButton("Supprimer Fournisseur");

        JPanel fournisseursButtonPanel = new JPanel();
        fournisseursButtonPanel.add(btnAjouterFournisseur);
        fournisseursButtonPanel.add(btnModifierFournisseur);
        fournisseursButtonPanel.add(btnSupprimerFournisseur);

        fournisseursPanel.add(fournisseursButtonPanel, BorderLayout.SOUTH);

        // Affichage des fournisseurs
        JTable fournisseursTable = new JTable();
        JScrollPane fournisseursScrollPane = new JScrollPane(fournisseursTable);
        fournisseursPanel.add(fournisseursScrollPane, BorderLayout.CENTER);

        DefaultTableModel fournisseursTableModel = new DefaultTableModel();
        fournisseursTableModel.addColumn("ID");
        fournisseursTableModel.addColumn("Nom");
        fournisseursTableModel.addColumn("Adresse");
        fournisseursTableModel.addColumn("Téléphone");
        fournisseursTable.setModel(fournisseursTableModel);

        FournisseurDAO fournisseurDAO = new FournisseurDAO();
        List<Fournisseur> fournisseurs = fournisseurDAO.getAllFournisseurs();
        for (Fournisseur fournisseur : fournisseurs) {
            fournisseursTableModel.addRow(new Object[]{fournisseur.getIdFournisseur(), fournisseur.getNomFournisseur(), fournisseur.getAdresseFournisseur(), fournisseur.getTelephoneFournisseur()});
        }

        tabbedPane.addTab("Fournisseurs", fournisseursPanel);
        this.fournisseursPanel = fournisseursPanel;

        

        getContentPane().add(tabbedPane);

        setLocationRelativeTo(null);
        setVisible(true);

        btnAjouterFournisseur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	AjouterFournisseur ajouterFournisseur = new AjouterFournisseur();
                ajouterFournisseur.setVisible(true);
                dispose();
            }
        });

        btnModifierFournisseur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = fournisseursTable.getSelectedRow();
                if (selectedRowIndex != -1) { 
                    int fournisseurId = (int) fournisseursTable.getValueAt(selectedRowIndex, 0); 
                    ModifierFournisseur modifierFournisseur = new ModifierFournisseur(fournisseurId);
                    modifierFournisseur.setVisible(true);
                    dispose();
   
                } 
                else {
                    JOptionPane.showMessageDialog(ResponsableAchat.this, "Veuillez sélectionner un fournisseur à modifier.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });


        btnSupprimerFournisseur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = fournisseursTable.getSelectedRow();
                if (selectedRowIndex != -1) { 
                    int fournisseurId = (int) fournisseursTable.getValueAt(selectedRowIndex, 0); 
                    try {
                        int option = JOptionPane.showConfirmDialog(ResponsableAchat.this, "Êtes-vous sûr de vouloir supprimer ce fournisseur ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            FournisseurDAO fournisseurDAO = new FournisseurDAO();
                            fournisseurDAO.deleteFournisseur(fournisseurId);
                            
                            dispose();
                            ResponsableAchat ra = new ResponsableAchat();

                            JOptionPane.showMessageDialog(ResponsableAchat.this, "Le fournisseur a été supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(ResponsableAchat.this, "Erreur lors de la suppression du fournisseur.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(ResponsableAchat.this, "Veuillez sélectionner un fournisseur à supprimer.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });


        JPanel bonsAchatPanel = new JPanel();
        bonsAchatPanel.setLayout(new BorderLayout());
                   
        // Affichage des bons d'achat
        JTable bonsAchatTable = new JTable();
        JScrollPane bonsAchatScrollPane = new JScrollPane(bonsAchatTable);
        bonsAchatPanel.add(bonsAchatScrollPane, BorderLayout.CENTER);

        DefaultTableModel bonsAchatTableModel = new DefaultTableModel();
        bonsAchatTableModel.addColumn("ID");
        bonsAchatTableModel.addColumn("ID Fournisseur");
        bonsAchatTableModel.addColumn("ID Article");
        bonsAchatTableModel.addColumn("Date");
        bonsAchatTableModel.addColumn("Montant Total");
        bonsAchatTable.setModel(bonsAchatTableModel);

        BonAchatDAO bonAchatDAO = new BonAchatDAO();
        List<BonAchat> bonsAchat = bonAchatDAO.getAllBonsAchat();
        for (BonAchat bonAchat : bonsAchat) {
            bonsAchatTableModel.addRow(new Object[]{bonAchat.getIdBonAchat(),bonAchat.getIdFournisseur(),bonAchat.getIdArticle() ,bonAchat.getDateAchat(), bonAchat.getMontantTotal()});
        }

        tabbedPane.addTab("Bons d'Achat", bonsAchatPanel);
        JButton btnAjouterBonAchat = new JButton("Ajouter Bon d'Achat");
        JButton btnModifierBonAchat = new JButton("Modifier Bon d'Achat");
        JButton btnSupprimerBonAchat = new JButton("Supprimer Bon d'Achat");

        JPanel bonsAchatButtonPanel = new JPanel();
        bonsAchatButtonPanel.add(btnAjouterBonAchat);
        bonsAchatButtonPanel.add(btnModifierBonAchat);
        bonsAchatButtonPanel.add(btnSupprimerBonAchat);

        bonsAchatPanel.add(bonsAchatButtonPanel, BorderLayout.SOUTH);
        
        
        btnAjouterBonAchat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AjouterBonAchat ajouterBonAchat = new AjouterBonAchat();
                ajouterBonAchat.setVisible(true);
                dispose();
            }
        });
        btnModifierBonAchat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				int selectedRowIndex = bonsAchatTable.getSelectedRow();
                if (selectedRowIndex != -1) { 
                    int bonAchatId = (int) bonsAchatTable.getValueAt(selectedRowIndex, 0);
                    ModifierBonAchat modifierBonAchat = new ModifierBonAchat(bonAchatId);
                    modifierBonAchat.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(ResponsableAchat.this, "Veuillez sélectionner un bon d'achat à modifier.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnSupprimerBonAchat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = bonsAchatTable.getSelectedRow();
                if (selectedRowIndex != -1) { 
                    int bonAchatId = (int) bonsAchatTable.getValueAt(selectedRowIndex, 0); 
                    try {
                        
                        int option = JOptionPane.showConfirmDialog(ResponsableAchat.this, "Êtes-vous sûr de vouloir supprimer ce bon d'achat ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            
                            BonAchatDAO bonAchatDAO = new BonAchatDAO();
                            bonAchatDAO.deleteBonAchat(bonAchatId);

                            dispose();
                            ResponsableAchat ra = new ResponsableAchat();
                            JOptionPane.showMessageDialog(ResponsableAchat.this, "Le bon d'achat a été supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(ResponsableAchat.this, "Erreur lors de la suppression du bon d'achat.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(ResponsableAchat.this, "Veuillez sélectionner un bon d'achat à supprimer.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

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
                    JOptionPane.showMessageDialog(ResponsableAchat.this, "Erreur lors de la recherche par désignation.", "Erreur", JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(ResponsableAchat.this, "Erreur lors de la recherche par catégorie.", "Erreur", JOptionPane.ERROR_MESSAGE);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
                try {
					new ResponsableAchat();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
        });
    }
}
