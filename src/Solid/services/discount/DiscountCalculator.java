package Solid.services.discount;

import Solid.core.Order;
import java.util.ArrayList;
import java.util.List;

public class DiscountCalculator {
    private final List<DiscountStrategy> activeStrategies = new ArrayList<>();

    public void addStrategy(DiscountStrategy strategy) {
        activeStrategies.add(strategy);
    }

    public double calculateTotalDiscount(Order order) {
        return activeStrategies.stream()
                .mapToDouble(s -> s.calculateDiscount(order))
                .sum();
    }
}