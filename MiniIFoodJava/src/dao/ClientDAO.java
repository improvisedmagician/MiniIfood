package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connection.ConnectionFactory;
import model.Client;

public class ClientDAO {
    public void insert(Client client) throws SQLException {
        String sql = "INSERT INTO Client (name, email, address, cpf, city, state, cep) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getEmail());
            stmt.setString(3, client.getAddress());
            stmt.setString(4, client.getCpf());
            stmt.setString(5, client.getCity());
            stmt.setString(6, client.getState());
            stmt.setString(7, client.getCep());
            stmt.executeUpdate();
        }
    }

    public List<Client> getAll() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM Client";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clients.add(new Client(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getString("cpf"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("cep")
                ));
            }
        }
        return clients;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Client WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void update(Client client) throws SQLException {
        String sql = "UPDATE Client SET name = ?, email = ?, address = ?, cpf = ?, city = ?, state = ?, cep = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getEmail());
            stmt.setString(3, client.getAddress());
            stmt.setString(4, client.getCpf());
            stmt.setString(5, client.getCity());
            stmt.setString(6, client.getState());
            stmt.setString(7, client.getCep());
            stmt.setInt(8, client.getId());
            stmt.executeUpdate();
        }
    }
}
