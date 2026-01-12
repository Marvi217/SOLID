package Solid.services.storage;

import Solid.core.Customer;
import java.sql.*;
import java.util.Optional;

public class JdbcCustomerRepository {
    private final String url = "jdbc:mysql://localhost:3306/retail_db";
    private final String user = "root";
    private final String password = "password";

    public void saveOrUpdate(Customer customer) {
        String sql = "INSERT INTO customers (id, name, email, type, total_value) " +
                "VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE " +
                "email=VALUES(email), type=VALUES(type), total_value=VALUES(total_value)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customer.getId());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getType());
            pstmt.setDouble(5, 500.0);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Customer> findById(int id) {
        return Optional.empty();
    }
}