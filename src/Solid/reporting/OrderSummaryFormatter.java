package Solid.reporting;

import Solid.core.Order;

public class OrderSummaryFormatter implements ReportFormatter<Order> {
    @Override
    public String format(Order order) {
        return "PODSUMOWANIE ZAMÓWIENIA #" + order.getId() + "\n" +
                "Suma: " + order.getTotalAmount() + " PLN";
    }
}
