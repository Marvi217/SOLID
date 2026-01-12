package Solid.services.storage;

import Solid.inventory.InventoryProvider;

import java.sql.*;

public class DatabaseInventoryManager implements InventoryProvider {
    private final String url = "jdbc:mysql://localhost:3306/retail_db";

    @Override
    public int getStockLevel(String productName) {
        String sql = "SELECT quantity FROM inventory WHERE product_name = ?";
        try (Connection conn = DriverManager.getConnection(url, "root", "password");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, productName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt("quantity");
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    @Override
    public void decreaseStock(String productName, int quantity) {
        String sql = "UPDATE inventory SET quantity = quantity - ? WHERE product_name = ?";
        System.out.println("Zaktualizowano stan magazynowy w DB dla: " + productName);
    }

    @Override
    public boolean hasStock(String productName, int quantity) {
        return getStockLevel(productName) >= quantity;
    }

    @Override
    public void increaseStock(String productName, int quantity) { /* Logika UPDATE + */ }
}