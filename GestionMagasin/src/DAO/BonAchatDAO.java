package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Métier.BonAchat;

public class BonAchatDAO {
    Connection connection=SingletonConnection.getInstance();


    public void insertBonAchat(BonAchat bonAchat) throws SQLException {
        String sql = "INSERT INTO bons_achat (id_fournisseur,idArticle, date_achat, montant_total) VALUES (?,?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bonAchat.getIdFournisseur());
            statement.setInt(2, bonAchat.getIdArticle());
            statement.setDate(3, new java.sql.Date(bonAchat.getDateAchat().getTime()));
            statement.setDouble(4, bonAchat.getMontantTotal());
            statement.executeUpdate();
        }
    }

    public void updateBonAchat(BonAchat bonAchat) throws SQLException {
        String sql = "UPDATE bons_achat SET date_achat = ?, montant_total = ? WHERE id_bon_achat = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, new java.sql.Date(bonAchat.getDateAchat().getTime()));
            statement.setDouble(2, bonAchat.getMontantTotal());
            statement.setInt(3, bonAchat.getIdBonAchat());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("La mise à jour du bon d'achat a échoué, aucun enregistrement n'a été modifié.");
            }
        }
    }

    public void deleteBonAchat(int idBonAchat) throws SQLException {
        String sql = "DELETE FROM bons_achat WHERE id_bon_achat = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idBonAchat);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new SQLException("La suppression du bon d'achat a échoué, aucun enregistrement n'a été supprimé.");
            }
        }
    }

    public List<BonAchat> getAllBonsAchat() throws SQLException {
        List<BonAchat> bonsAchat = new ArrayList<>();
        String sql = "SELECT * FROM bons_achat";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idBonAchat = resultSet.getInt("id_bon_achat");
                    int idFournisseur = resultSet.getInt("id_fournisseur");
                    int idArticle = resultSet.getInt("idArticle");
                    Date dateAchat = resultSet.getDate("date_achat");
                    double montantTotal = resultSet.getDouble("montant_total");
                    BonAchat bonAchat = new BonAchat(idBonAchat, idFournisseur,idArticle, dateAchat, montantTotal);
                    bonsAchat.add(bonAchat);
                }
            }
        }
        return bonsAchat;
    }

    public BonAchat getBonAchatById(int bonAchatId) throws SQLException {
        BonAchat bonAchat = null;
        String query = "SELECT * FROM bon_achat WHERE id_bon_achat = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bonAchatId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int idBonAchat = resultSet.getInt("id_bon_achat");
                    int idFournisseur = resultSet.getInt("id_fournisseur");
                    java.util.Date dateAchat = resultSet.getDate("date_achat");
                    double montantTotal = resultSet.getDouble("montant_total");
                    bonAchat = new BonAchat(idBonAchat, idFournisseur, dateAchat, montantTotal);
                }
            }
        }

        return bonAchat;
    }
    }

