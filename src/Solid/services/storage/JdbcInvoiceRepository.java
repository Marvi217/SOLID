package Solid.services.storage;

import Solid.core.Invoice;
import java.sql.*;

public class JdbcInvoiceRepository {
    private final String url = "jdbc:mysql://localhost:3306/retail_db";

    public void save(Invoice invoice) {
        String sql = "INSERT INTO invoices (invoice_number, order_id, issue_date, amount) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, "root", "password");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, invoice.getInvoiceNumber());
            pstmt.setInt(2, invoice.getOrder().getId());
            pstmt.setTimestamp(3, Timestamp.valueOf(invoice.getIssueDate()));
            pstmt.setDouble(4, invoice.getFinalAmount());

            pstmt.executeUpdate();
            System.out.println("[DB] Faktura " + invoice.getInvoiceNumber() + " została zapisana.");
        } catch (SQLException e) {
            System.err.println("Błąd zapisu faktury: " + e.getMessage());
        }
    }
}