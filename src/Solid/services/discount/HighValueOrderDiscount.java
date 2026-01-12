package Solid.services.discount;

import Solid.core.Order;

public class HighValueOrderDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(Order order) {
        double currentTotal = order.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        return currentTotal > 500 ? 50.0 : 0;
    }
}
