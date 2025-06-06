package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connection.ConnectionFactory;
import model.OrderItem;

public class OrderItemDAO {

    public void insert(OrderItem item) throws SQLException {
        String sql = "INSERT INTO OrderItem (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getOrderId());
            stmt.setInt(2, item.getProductId());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getUnitPrice());
            stmt.executeUpdate();
        }
    }

    public List<OrderItem> getAll() throws SQLException {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT * FROM OrderItem";
        try (Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new OrderItem(
                    rs.getInt("id"),
                    rs.getInt("order_id"),
                    rs.getInt("product_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("unit_price")
                ));
            }
        }
        return list;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM OrderItem WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void update(OrderItem item) throws SQLException {
        String sql = "UPDATE OrderItem SET order_id=?, product_id=?, quantity=?, unit_price=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getOrderId());
            stmt.setInt(2, item.getProductId());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getUnitPrice());
            stmt.setInt(5, item.getId());
            stmt.executeUpdate();
        }
    }
}
