package Solid.services.discount;

import Solid.core.Order;

public class VIPCustomerDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(Order order) {
        if ("DIAMOND".equals(order.getCustomer().getType())) {
            return (order.getTotalAmount() * 0.10);
        }
        return 0;
    }
}

