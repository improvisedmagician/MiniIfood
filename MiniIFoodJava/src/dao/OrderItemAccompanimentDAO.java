package dao;

import connection.ConnectionFactory;
import model.OrderItemAccompaniment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemAccompanimentDAO {

    public void insert(OrderItemAccompaniment oia) throws SQLException {
        String sql = "INSERT INTO OrderItemAccompaniment (order_item_id, accompaniment_id) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, oia.getOrderItemId());
            stmt.setInt(2, oia.getAccompanimentId());
            stmt.executeUpdate();
        }
    }

    public List<OrderItemAccompaniment> getByOrderItemId(int orderItemId) throws SQLException {
        List<OrderItemAccompaniment> list = new ArrayList<>();
        String sql = "SELECT * FROM OrderItemAccompaniment WHERE order_item_id = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderItemId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new OrderItemAccompaniment(
                        rs.getInt("order_item_id"),
                        rs.getInt("accompaniment_id")
                    ));
                }
            }
        }
        return list;
    }

    public void deleteByOrderItemId(int orderItemId) throws SQLException {
        String sql = "DELETE FROM OrderItemAccompaniment WHERE order_item_id = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderItemId);
            stmt.executeUpdate();
        }
    }
}
