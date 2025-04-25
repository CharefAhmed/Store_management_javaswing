package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import DAO.ArticleDAO;
import Métier.Article;

public class AjouterArticle extends JDialog {
    private JTextField txtDesignation, txtCategorie, txtQuantite, txtPrixUnitaire, txtEtatArticle;
    private JButton btnAjouter, btnAnnuler;

    public AjouterArticle() {
        setTitle("Ajouter un article");
        setSize(400, 300);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2));

        JLabel lblDesignation = new JLabel("Désignation:");
        txtDesignation = new JTextField();
        panel.add(lblDesignation);
        panel.add(txtDesignation);

        JLabel lblCategorie = new JLabel("Catégorie:");
        txtCategorie = new JTextField();
        panel.add(lblCategorie);
        panel.add(txtCategorie);

        JLabel lblQuantite = new JLabel("Quantité:");
        txtQuantite = new JTextField();
        panel.add(lblQuantite);
        panel.add(txtQuantite);

        JLabel lblPrixUnitaire = new JLabel("Prix Unitaire:");
        txtPrixUnitaire = new JTextField();
        panel.add(lblPrixUnitaire);
        panel.add(txtPrixUnitaire);

        JLabel lblEtatArticle = new JLabel("État Article:");
        txtEtatArticle = new JTextField();
        panel.add(lblEtatArticle);
        panel.add(txtEtatArticle);

        btnAjouter = new JButton("Ajouter");
        btnAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterArticle();
            }
        });
        panel.add(btnAjouter);

        btnAnnuler = new JButton("Annuler");
        btnAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
					Magasiner ma = new Magasiner();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        panel.add(btnAnnuler);

        add(panel);
        setVisible(true);
    }

    private void ajouterArticle() {
        String designation = txtDesignation.getText();
        String categorie = txtCategorie.getText();
        int quantite = Integer.parseInt(txtQuantite.getText());
        double prixUnitaire = Double.parseDouble(txtPrixUnitaire.getText());
        String etatArticle = txtEtatArticle.getText();

        Article nouvelArticle = new Article(designation, categorie, quantite, prixUnitaire, etatArticle);
        ArticleDAO articleDAO = new ArticleDAO();
        try {
            articleDAO.insertArticle(nouvelArticle);
            JOptionPane.showMessageDialog(this, "Article ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            try {
				Magasiner ma = new Magasiner();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            try {
				Magasiner ma = new Magasiner();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'article.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AjouterArticle();
            }
        });
    }
}
