package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connection.ConnectionFactory;
import model.Restaurant;

public class RestaurantDAO {
    public void insert(Restaurant restaurant) throws SQLException {
        String sql = "INSERT INTO Restaurant (name, address, city) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, restaurant.getName());
            stmt.setString(2, restaurant.getAddress());
            stmt.setString(3, restaurant.getCity());
            stmt.executeUpdate();
        }
    }

    public List<Restaurant> getAll() throws SQLException {
        List<Restaurant> list = new ArrayList<>();
        String sql = "SELECT * FROM Restaurant";
        try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Restaurant(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("city")
                ));
            }
        }
        return list;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Restaurant WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void update(Restaurant restaurant) throws SQLException {
        String sql = "UPDATE Restaurant SET name = ?, address = ?, city = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, restaurant.getName());
            stmt.setString(2, restaurant.getAddress());
            stmt.setString(3, restaurant.getCity());
            stmt.setInt(4, restaurant.getId());
            stmt.executeUpdate();
        }
    }
}