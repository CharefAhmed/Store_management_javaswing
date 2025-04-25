package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Métier.Fournisseur;

public class FournisseurDAO {
    Connection connection=SingletonConnection.getInstance();


    public void insertFournisseur(Fournisseur fournisseur) throws SQLException {
        String sql = "INSERT INTO fournisseurs (nom_fournisseur, adresse_fournisseur, telephone_fournisseur) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, fournisseur.getNomFournisseur());
            statement.setString(2, fournisseur.getAdresseFournisseur());
            statement.setString(3, fournisseur.getTelephoneFournisseur());
            statement.executeUpdate();
        }
    }

    public void updateFournisseur(Fournisseur fournisseur) throws SQLException {
        String sql = "UPDATE fournisseurs SET nom_fournisseur = ?, adresse_fournisseur = ?, telephone_fournisseur = ? WHERE id_fournisseur = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, fournisseur.getNomFournisseur());
            statement.setString(2, fournisseur.getAdresseFournisseur());
            statement.setString(3, fournisseur.getTelephoneFournisseur());
            statement.setInt(4, fournisseur.getIdFournisseur());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("La mise à jour du fournisseur a échoué, aucun enregistrement n'a été modifié.");
            }
        }
    }

    public void deleteFournisseur(int idFournisseur) throws SQLException {
        String sql = "DELETE FROM fournisseurs WHERE id_fournisseur = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idFournisseur);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new SQLException("La suppression du fournisseur a échoué, aucun enregistrement n'a été supprimé.");
            }
        }
    }

    public List<Fournisseur> getAllFournisseurs() throws SQLException {
        List<Fournisseur> fournisseurs = new ArrayList<>();
        String sql = "SELECT * FROM fournisseurs";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idFournisseur = resultSet.getInt("id_fournisseur");
                    String nomFournisseur = resultSet.getString("nom_fournisseur");
                    String adresseFournisseur = resultSet.getString("adresse_fournisseur");
                    String telephoneFournisseur = resultSet.getString("telephone_fournisseur");
                    Fournisseur fournisseur = new Fournisseur(idFournisseur, nomFournisseur, adresseFournisseur, telephoneFournisseur);
                    fournisseurs.add(fournisseur);
                }
            }
        }
        return fournisseurs;
    }

    public Fournisseur getFournisseurById(int fournisseurId) throws SQLException {
        String query = "SELECT * FROM fournisseurs WHERE id_fournisseur = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Fournisseur fournisseur = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, fournisseurId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                fournisseur = new Fournisseur(
                        resultSet.getInt("id_fournisseur"),
                        resultSet.getString("nom_fournisseur"),
                        resultSet.getString("adresse_fournisseur"),
                        resultSet.getString("telephone_fournisseur")
                );
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }

        return fournisseur; 
    }
    public void updateFournisseur(int fournisseurId, String nom, String adresse, String telephone) throws SQLException {
        String query = "UPDATE fournisseurs SET nom_fournisseur = ?, adresse_fournisseur = ?, telephone_fournisseur = ? WHERE id_fournisseur = ?";

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, nom);
            statement.setString(2, adresse);
            statement.setString(3, telephone);
            statement.setInt(4, fournisseurId);
            statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }
    public List<String> getAllFournisseursByName() throws SQLException {
        List<String> fournisseurs = new ArrayList<>();
        String query = "SELECT nom_fournisseur FROM fournisseurs"; 

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String nomFournisseur = resultSet.getString("nom_fournisseur");
                fournisseurs.add(nomFournisseur);
            }
        }

        return fournisseurs;
    }

}
