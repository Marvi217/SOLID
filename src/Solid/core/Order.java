package Solid.core;

import Solid.services.tax.TaxStrategy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private final int id;
    private final Customer customer;
    private final List<OrderItem> items = new ArrayList<>();
    private final Date createdAt;
    private String status = "NEW";

    private double taxAmount;
    private double discountAmount;
    private double totalAmount;

    public Order(int id, Customer customer) {
        this.id = id;
        this.customer = customer;
        this.createdAt = new Date();
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    public double calculateFinalAmount(TaxStrategy taxStrategy, double discount) {
        double base = items.stream().mapToDouble(OrderItem::getSubtotal).sum();

        this.taxAmount = taxStrategy.calculateTax(base);
        this.discountAmount = discount;
        this.totalAmount = Math.max(0, base + taxAmount - discountAmount);

        return this.totalAmount;
    }

    // Getters
    public int getId() { return id; }
    public Customer getCustomer() { return customer; }
    public List<OrderItem> getItems() { return items; }
    public double getTotalAmount() { return totalAmount; }
    public double getTaxAmount() { return taxAmount; }
    public double getDiscountAmount() { return discountAmount; }
}