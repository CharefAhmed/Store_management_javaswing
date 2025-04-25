package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Métier.Client;

public class ClientDAO {
    Connection connection=SingletonConnection.getInstance();


    public void insertClient(Client client) throws SQLException {
        String sql = "INSERT INTO clients VALUES (?,?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, client.getIdClient());
        	statement.setString(2, client.getNomClient());
            statement.setString(3, client.getAdresseClient());
            statement.setString(4, client.getTelephoneClient());
            statement.executeUpdate();
        }
    }
    public void deleteClient(Client client)throws SQLException{
        String sql ="delete from clients where id_Client = ? ";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,client.getIdClient());
            statement.executeUpdate();
        }
    }

    public void updateClient(Client client) throws SQLException {
        String sql = "UPDATE clients SET nom_client = ?, adresse_client = ?, telephone_client = ? WHERE id_client = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, client.getNomClient());
            statement.setString(2, client.getAdresseClient());
            statement.setString(3, client.getTelephoneClient());
            statement.setInt(4, client.getIdClient());
            statement.executeUpdate();
        }
    }

    public List<Client> getAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idClient = resultSet.getInt("id_client");
                    String nomClient = resultSet.getString("nom_client");
                    String adresseClient = resultSet.getString("adresse_client");
                    String telephoneClient = resultSet.getString("telephone_client");
                    Client client = new Client(idClient, nomClient, adresseClient, telephoneClient);
                    clients.add(client);
                }
            }
        }
        return clients;
    }

    public Client getClientById(int id) throws SQLException {
        String sql = "SELECT * FROM clients WHERE id_client = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int idClient = resultSet.getInt("id_client");
                    String nomClient = resultSet.getString("nom_client");
                    String adresseClient = resultSet.getString("adresse_client");
                    String telephoneClient = resultSet.getString("telephone_client");
                    return new Client(idClient, nomClient, adresseClient, telephoneClient);
                }
            }
        }
        return null; 
    }
}
