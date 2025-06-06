package dao;

import connection.ConnectionFactory;
import model.PaymentMethod;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentMethodDAO {
    public List<PaymentMethod> getAll() throws SQLException {
        List<PaymentMethod> list = new ArrayList<>();
        String sql = "SELECT * FROM PaymentMethod";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new PaymentMethod(
                    rs.getInt("id"),
                    rs.getString("description")
                ));
            }
        }
        return list;
    }
}
