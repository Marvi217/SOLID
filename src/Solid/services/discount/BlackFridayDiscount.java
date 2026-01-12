package Solid.services.discount;

import Solid.core.Order;

public class BlackFridayDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(Order order) {
        return order.getTotalAmount() * 0.20;
    }
}
