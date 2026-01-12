package Solid.core;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final int id;
    private final String name;
    private String email;
    private String type = "STANDARD";
    private double lifetimeValue = 0;
    private int loyaltyPoints = 0;
    private final List<Integer> orderHistory = new ArrayList<>();

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addOrderToHistory(int orderId, double value) {
        this.orderHistory.add(orderId);
        this.lifetimeValue += value;
        updateTier();
    }

    private void updateTier() {
        if (lifetimeValue > 50000) type = "DIAMOND";
        else if (lifetimeValue > 30000) type = "PLATINUM";
        else if (lifetimeValue > 15000) type = "GOLD";
        else if (lifetimeValue > 7000) type = "SILVER";
        else if (lifetimeValue > 3000) type = "BRONZE";
        else type = "STANDARD";
    }

    // Getters & Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getType() { return type; }
}
