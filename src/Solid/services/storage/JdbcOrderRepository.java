package Solid.services.storage;

import Solid.core.Order;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class JdbcOrderRepository implements OrderRepository {
    private final String url = "jdbc:mysql://localhost:3306/";
    private final String user = "root";
    private final String password = "password";

    @Override
    public void save(Order order) {
        String sql = "INSERT INTO orders (id, customer_name, total_amount) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, order.getId());
            pstmt.setString(2, order.getCustomer().getName());
            pstmt.setDouble(3, order.getTotalAmount());

            pstmt.executeUpdate();
            System.out.println("Zamówienie zapisane w bazie MySQL.");
        } catch (SQLException e) {
            System.err.println("Błąd bazy danych: " + e.getMessage());
        }
    }

    @Override
    public Optional<Order> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Order> findAll() {
        return List.of();
    }

    @Override
    public void delete(int id) {

    }
}
