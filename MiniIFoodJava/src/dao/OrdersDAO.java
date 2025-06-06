package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import connection.ConnectionFactory;
import model.Orders;

public class OrdersDAO {
    public void insert(Orders order) throws SQLException {
        String sql = "INSERT INTO `Order` (client_id, restaurant_id, order_date, delivery_time, payment_method_id, total_price) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getClientId());
            stmt.setInt(2, order.getRestaurantId());
            stmt.setTimestamp(3, Timestamp.valueOf(order.getOrderDate()));
            stmt.setInt(4, order.getDeliveryTime());
            stmt.setInt(5, order.getPaymentMethodId());
            stmt.setDouble(6, order.getTotalPrice());
            stmt.executeUpdate();
        }
    }

    public List<Orders> getAll() throws SQLException {
        List<Orders> list = new ArrayList<>();
        String sql = "SELECT * FROM `Order`";
        try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Orders(
                    rs.getInt("id"),
                    rs.getInt("client_id"),
                    rs.getInt("restaurant_id"),
                    rs.getTimestamp("order_date").toLocalDateTime(),
                    rs.getInt("delivery_time"),
                    rs.getInt("payment_method_id"),
                    rs.getDouble("total_price")
                ));
            }
        }
        return list;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM `Order` WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void update(Orders order) throws SQLException {
        String sql = "UPDATE `Order` SET client_id=?, restaurant_id=?, order_date=?, delivery_time=?, payment_method_id=?, total_price=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getClientId());
            stmt.setInt(2, order.getRestaurantId());
            stmt.setTimestamp(3, Timestamp.valueOf(order.getOrderDate()));
            stmt.setInt(4, order.getDeliveryTime());
            stmt.setInt(5, order.getPaymentMethodId());
            stmt.setDouble(6, order.getTotalPrice());
            stmt.setInt(7, order.getId());
            stmt.executeUpdate();
        }
    }
}
