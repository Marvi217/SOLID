package Solid.services.discount;

import Solid.core.Order;

public interface DiscountStrategy {
    double calculateDiscount(Order order);
}