package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Métier.Commande;

public class CommandeDAO {
   Connection connection=SingletonConnection.getInstance();

  

    public void insertCommande(Commande commande) throws SQLException {
        String sql = "INSERT INTO commandes (id_client,idArticle,designation,quantite,date_commande, montant_total) VALUES (?, ?, ?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, commande.getIdClient());
            statement.setInt(2, commande.getIdArticle());
            statement.setString(3, commande.getDesignation());
            statement.setInt(4, commande.getQuantite());
            statement.setDate(5, new java.sql.Date(commande.getDateCommande().getTime()));
            statement.setDouble(6, commande.getMontantTotal());
            statement.executeUpdate();
        }
    }

    public void updateCommande(Commande commande) throws SQLException {
        String sql = "UPDATE commandes SET id_client = ?,idArticle=?, designation=?,quantite=?,date_commande = ?, montant_total = ? WHERE id_commande = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, commande.getIdClient());
            statement.setInt(2, commande.getIdArticle());
            statement.setString(3, commande.getDesignation());
            statement.setInt(4, commande.getQuantite());
            statement.setDate(5, new java.sql.Date(commande.getDateCommande().getTime()));
            statement.setDouble(6, commande.getMontantTotal());
            statement.setInt(7, commande.getIdCommande());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("La mise à jour de la commande a échoué, aucun enregistrement n'a été modifié.");
            }
        }
    }

    public void deleteCommande(int idCommande) throws SQLException {
        String sql = "DELETE FROM commandes WHERE id_commande = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCommande);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new SQLException("La suppression de la commande a échoué, aucun enregistrement n'a été supprimé.");
            }
        }
    }

    public  List<Commande> getAllCommandes() throws SQLException {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commandes";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idCommande = resultSet.getInt("id_commande");
                    int idClient = resultSet.getInt("id_client");
                     int idArticle=resultSet.getInt("idArticle");
                     String designation=resultSet.getString("designation");
                     int quantite=resultSet.getInt("quantite");
                    Date dateCommande = resultSet.getDate("date_commande");
                    double montantTotal = resultSet.getDouble("montant_total");
                    Commande commande = new Commande(idCommande,idClient,idArticle,designation,quantite,dateCommande, montantTotal);
                    commandes.add(commande);
                }
            }
        }
        return commandes;
    }
    public Commande getCommandeById(int commandeId) throws SQLException {
        Commande commande = null;
        String sql = "SELECT * FROM commandes WHERE id_commande = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, commandeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int idCommande = resultSet.getInt("id_commande");
                    int idClient = resultSet.getInt("id_client");
                    int idArticle=resultSet.getInt("idArticle");
                    String designation=resultSet.getString("designation");
                    int quantite=resultSet.getInt("quantite");
                    Date dateCommande = resultSet.getDate("date_commande");
                    double montantTotal = resultSet.getDouble("montant_total");
                    commande = new Commande(idCommande, idClient,idArticle,designation,quantite, dateCommande, montantTotal);
                }
            }
        }

        return commande;
    }
    public void setDateCommande(int commandeId, Date newDate) throws SQLException {
        String sql = "UPDATE commande SET date_commande = ? WHERE id_commande = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, new java.sql.Date(newDate.getTime()));
            statement.setInt(2, commandeId);
            statement.executeUpdate();
        }
    }

}
