package Presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DAO.ArticleDAO;
import Métier.Article;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Magasiner extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel articlesPanel;
    private JTable articlesTable;
    private JTextField txtRechercheDesignation;
    private JTextField txtRechercheCategorie;
    private JTextField txtRechercheEtat;

    public Magasiner() throws SQLException {
        setTitle("Magasinier");
        setSize(1900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        // Affichage des articles
        articlesPanel = new JPanel();
        articlesPanel.setLayout(null);
        articlesTable = new JTable();
        JScrollPane articlesScrollPane = new JScrollPane(articlesTable);
        articlesScrollPane.setBounds(0, 0, 1370, 499);
        articlesPanel.add(articlesScrollPane);

        DefaultTableModel articlesTableModel = new DefaultTableModel();
        articlesTableModel.addColumn("ID");
        articlesTableModel.addColumn("Désignation");
        articlesTableModel.addColumn("Catégorie");
        articlesTableModel.addColumn("Quantité");
        articlesTableModel.addColumn("Prix Unitaire");
        articlesTableModel.addColumn("Etat Article");

        articlesTable.setModel(articlesTableModel);

        ArticleDAO ar = new ArticleDAO();
        List<Article> articles = ar.getAllArticles();
        for (Article article : articles) {
        	if (article.getQuantite()==0) {
        		article.setEtatArt("hors stock");
        		ar.updateArticle(article);
        	}
        	articlesTableModel.addRow(new Object[]{article.getIdArticle(), article.getDesignation(), article.getCategorie(), article.getQuantite(), article.getPrixUnitaire(), article.getEtatArt()});
        }

        tabbedPane.addTab("Articles", articlesPanel);

        getContentPane().add(tabbedPane);

        setLocationRelativeTo(null);
        setVisible(true);

        txtRechercheDesignation = new JTextField(20);
        txtRechercheDesignation.setBounds(146, 6, 166, 19);
        txtRechercheCategorie = new JTextField(20);
        txtRechercheCategorie.setBounds(639, 6, 166, 19);

        JPanel searchPanel = new JPanel();
        searchPanel.setBounds(0, 499, 1535, 37);
        searchPanel.setLayout(null);
        JLabel label = new JLabel("Recherche par Désignation:");
        label.setBounds(10, 9, 126, 13);
        searchPanel.add(label);
        searchPanel.add(txtRechercheDesignation);
        JButton btnRechercheDesignation = new JButton("Recherche Désignation");
        btnRechercheDesignation.setBounds(322, 5, 137, 21);
        searchPanel.add(btnRechercheDesignation);
        JLabel label_1 = new JLabel("Recherche par Catégorie:");
        label_1.setBounds(511, 9, 118, 13);
        searchPanel.add(label_1);
        searchPanel.add(txtRechercheCategorie);
        JButton btnRechercheCategorie = new JButton("Recherche Catégorie");
        btnRechercheCategorie.setBounds(815, 5, 127, 21);
        searchPanel.add(btnRechercheCategorie);
        JLabel label_2 = new JLabel("Recherche par Etat:");
        label_2.setBounds(973, 7, 126, 16);
        searchPanel.add(label_2);
        articlesPanel.add(searchPanel);

        JButton btnRechercheEtatArticle = new JButton("Recherche par État");
        btnRechercheEtatArticle.setBounds(1258, 2, 143, 27);
        searchPanel.add(btnRechercheEtatArticle);
        txtRechercheEtat = new JTextField(20);
        txtRechercheEtat.setBounds(1093, 6, 143, 19);
        searchPanel.add(txtRechercheEtat);
        
                btnRechercheEtatArticle.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String etatArt = txtRechercheEtat.getText();
                        try {
                            ArticleDAO articleDAO = new ArticleDAO();
                            List<Article> articles = articleDAO.getArticlesByEtat(etatArt);
                            DefaultTableModel model = (DefaultTableModel) articlesTable.getModel();
                            model.setRowCount(0); 
                            for (Article article : articles) {
                                model.addRow(new Object[]{article.getIdArticle(), article.getDesignation(), article.getCategorie(), article.getQuantite(), article.getPrixUnitaire(), article.getEtatArt()});
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(Magasiner.this, "Erreur lors de la recherche par catégorie.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
        
                JButton btnAjouterArticle = new JButton("Ajouter Article");
                btnAjouterArticle.setBounds(1380, 119, 145, 21);
                articlesPanel.add(btnAjouterArticle);
                
                JButton btnModifierArticle = new JButton("Modifier Article");
                btnModifierArticle.setBounds(1380, 238, 145, 21);
                        articlesPanel.add(btnModifierArticle);
                        
               // Bouton pour supprimer un article
               JButton btnSupprimerArticle = new JButton("Supprimer Article");
               btnSupprimerArticle.setBounds(1380, 348, 145, 21);
               articlesPanel.add(btnSupprimerArticle);
                                
               JButton btnRefresh = new JButton("Refresh");
               btnRefresh.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
            	   dispose();
                   try {
                	   Magasiner ma =new Magasiner();
                   } catch (SQLException e1) {
                	   e1.printStackTrace();
                   }
               }
              });
                                btnRefresh.setBounds(1405, 431, 85, 21);
                                articlesPanel.add(btnRefresh);
                                
                                        btnSupprimerArticle.addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                int selectedRow = articlesTable.getSelectedRow();
                                                if (selectedRow != -1) {
                                                    int idArticle = (int) articlesTable.getValueAt(selectedRow, 0);
                                                    int option = JOptionPane.showConfirmDialog(Magasiner.this, "Êtes-vous sûr de vouloir supprimer cet article ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
                                                    if (option == JOptionPane.YES_OPTION) {
                                                        try {
                                                            ArticleDAO articleDAO = new ArticleDAO();
                                                            Article article = articleDAO.getArticleById(idArticle);
                                                            articleDAO.deleteArticle(article);
                                                            DefaultTableModel model = (DefaultTableModel) articlesTable.getModel();
                                                            model.removeRow(selectedRow);
                                                            JOptionPane.showMessageDialog(Magasiner.this, "Article supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                                                        } catch (SQLException ex) {
                                                            ex.printStackTrace();
                                                            JOptionPane.showMessageDialog(Magasiner.this, "Erreur lors de la suppression de l'article.", "Erreur", JOptionPane.ERROR_MESSAGE);
                                                        }
                                                    }
                                                } else {
                                                    JOptionPane.showMessageDialog(Magasiner.this, "Veuillez sélectionner un article à supprimer.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                                                }
                                            }
                                        });
                        
                                btnModifierArticle.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        int selectedRow = articlesTable.getSelectedRow();
                                        if (selectedRow != -1) {
                                            int idArticle = (int) articlesTable.getValueAt(selectedRow, 0);
                                            String designation = (String) articlesTable.getValueAt(selectedRow, 1);
                                            String categorie = (String) articlesTable.getValueAt(selectedRow, 2);
                                            int quantite = (int) articlesTable.getValueAt(selectedRow, 3);
                                            double prixUnitaire = (double) articlesTable.getValueAt(selectedRow, 4);
                                            String etatArt = (String) articlesTable.getValueAt(selectedRow, 5);
                                            Article article=new Article (idArticle,designation,categorie,quantite,prixUnitaire,etatArt);
                                            dispose();
                                            new ModifierArticle(article);
                                            
                                        } else {
                                            JOptionPane.showMessageDialog(Magasiner.this, "Veuillez sélectionner un article à modifier.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                                        }
                                    }
                                });
                
                        btnAjouterArticle.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                
                                dispose();
                                new AjouterArticle();
                                
                            }
                        });

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
                    JOptionPane.showMessageDialog(Magasiner.this, "Erreur lors de la recherche par désignation.", "Erreur", JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(Magasiner.this, "Erreur lors de la recherche par catégorie.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        Dimension preferredSize = new Dimension(1900, 600);
        setPreferredSize(preferredSize);
        pack(); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Magasiner();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de la création de la fenêtre du magasinier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
