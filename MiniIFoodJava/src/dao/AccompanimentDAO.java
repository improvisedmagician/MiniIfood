package dao;

import connection.ConnectionFactory;
import model.Accompaniment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccompanimentDAO {

    public void insert(Accompaniment accompaniment) throws SQLException {
        String sql = "INSERT INTO Accompaniment (name, price) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accompaniment.getName());
            stmt.setDouble(2, accompaniment.getPrice());
            stmt.executeUpdate();
        }
    }

    public List<Accompaniment> getAll() throws SQLException {
        List<Accompaniment> list = new ArrayList<>();
        String sql = "SELECT * FROM Accompaniment";
        try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Accompaniment(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price")
                ));
            }
        }
        return list;
    }

    public void update(Accompaniment accompaniment) throws SQLException {
        String sql = "UPDATE Accompaniment SET name=?, price=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accompaniment.getName());
            stmt.setDouble(2, accompaniment.getPrice());
            stmt.setInt(3, accompaniment.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Accompaniment WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
