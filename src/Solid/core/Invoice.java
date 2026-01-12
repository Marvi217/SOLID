package Solid.core;

import java.time.LocalDateTime;

public class Invoice {
    private final String invoiceNumber;
    private final Order order;
    private final LocalDateTime issueDate;
    private final double finalAmount;

    public Invoice(String invoiceNumber, Order order) {
        this.invoiceNumber = invoiceNumber;
        this.order = order;
        this.issueDate = LocalDateTime.now();
        this.finalAmount = order.getTotalAmount();
    }

    // Getters
    public String getInvoiceNumber() { return invoiceNumber; }
    public Order getOrder() { return order; }
    public LocalDateTime getIssueDate() { return issueDate; }
    public double getFinalAmount() { return finalAmount; }
}