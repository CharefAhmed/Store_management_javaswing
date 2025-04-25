package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import DAO.ArticleDAO;
import Métier.Article;

public class ModifierArticle extends JDialog {
    private JTextField txtDesignation, txtCategorie, txtQuantite, txtPrixUnitaire, txtEtatArticle;
    private JButton btnModifier, btnAnnuler;

    private Article article;

    public ModifierArticle(Article article) {
        setTitle("Modifier un article");
        setSize(400, 300);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        this.article = article;

        JPanel panel = new JPanel(new GridLayout(6, 2));

        JLabel lblDesignation = new JLabel("Désignation:");
        txtDesignation = new JTextField(article.getDesignation());
        panel.add(lblDesignation);
        panel.add(txtDesignation);

        JLabel lblCategorie = new JLabel("Catégorie:");
        txtCategorie = new JTextField(article.getCategorie());
        panel.add(lblCategorie);
        panel.add(txtCategorie);

        JLabel lblQuantite = new JLabel("Quantité:");
        txtQuantite = new JTextField(String.valueOf(article.getQuantite()));
        panel.add(lblQuantite);
        panel.add(txtQuantite);

        JLabel lblPrixUnitaire = new JLabel("Prix Unitaire:");
        txtPrixUnitaire = new JTextField(String.valueOf(article.getPrixUnitaire()));
        panel.add(lblPrixUnitaire);
        panel.add(txtPrixUnitaire);

        JLabel lblEtatArticle = new JLabel("État Article:");
        txtEtatArticle = new JTextField(article.getEtatArt());
        panel.add(lblEtatArticle);
        panel.add(txtEtatArticle);

        btnModifier = new JButton("Modifier");
        btnModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierArticle();
            }
        });
        panel.add(btnModifier);

        btnAnnuler = new JButton("Annuler");
        btnAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
                try {
    				Magasiner ma = new Magasiner();
    			} catch (SQLException e1) {
    				
    				e1.printStackTrace();
    			}
            }
        });
        panel.add(btnAnnuler);

        add(panel);
        setVisible(true);
    }

    private void modifierArticle() {
        String designation = txtDesignation.getText();
        String categorie = txtCategorie.getText();
        int quantite = Integer.parseInt(txtQuantite.getText());
        double prixUnitaire = Double.parseDouble(txtPrixUnitaire.getText());
        String etatArticle = txtEtatArticle.getText();

        article.setDesignation(designation);
        article.setCategorie(categorie);
        article.setQuantite(quantite);
        article.setPrixUnitaire(prixUnitaire);
        article.setEtatArt(etatArticle);

        ArticleDAO articleDAO = new ArticleDAO();
        try {
            articleDAO.updateArticle(article);
            JOptionPane.showMessageDialog(this, "Article modifié avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            try {
				Magasiner ma = new Magasiner();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification de l'article.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	Article article = new Article(1, "Nom de l'article", "Catégorie de l'article", 1, 1, "");
                new ModifierArticle(article);
            }
        });
    }
}
