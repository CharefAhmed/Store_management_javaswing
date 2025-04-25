package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Métier.Utilisateur;

public class UtilisateurDAO {
    Connection connection=SingletonConnection.getInstance();

    public void insertUtilisateur(Utilisateur utilisateur) throws SQLException {
        String sql = "INSERT INTO utilisateurs (nom_utilisateur, mot_de_passe, type_utilisateur) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, utilisateur.getNomUtilisateur());
            statement.setString(2, utilisateur.getMotDePasse());
            statement.setString(3, utilisateur.getTypeUtilisateur());
            statement.executeUpdate();
        }
    }

    public List<Utilisateur> getAllUtilisateurs() throws SQLException {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM utilisateurs";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idUtilisateur = resultSet.getInt("id_utilisateur");
                    String nomUtilisateur = resultSet.getString("nom_utilisateur");
                    String motDePasse = resultSet.getString("mot_de_passe");
                    String typeUtilisateur = resultSet.getString("type_utilisateur");
                    Utilisateur utilisateur = new Utilisateur(idUtilisateur, nomUtilisateur, motDePasse, typeUtilisateur);
                    utilisateurs.add(utilisateur);
                }
            }
        }
        return utilisateurs;
    }

    public Utilisateur getUtilisateurByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM utilisateurs WHERE nom_utilisateur = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int idUtilisateur = resultSet.getInt("id_utilisateur");
                    String nomUtilisateur = resultSet.getString("nom_utilisateur");
                    String motDePasse = resultSet.getString("mot_de_passe");
                    String typeUtilisateur = resultSet.getString("type_utilisateur");
                    return new Utilisateur(idUtilisateur, nomUtilisateur, motDePasse, typeUtilisateur);
                }
            }
        }
        return null;
    }
}

