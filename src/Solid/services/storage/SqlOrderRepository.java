package Solid.services.storage;

import Solid.core.Order;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class SqlOrderRepository implements OrderRepository {

    @Override
    public void save(Order order) {
        System.out.println("SQL: Zapisywanie zamówienia #" + order.getId() + " do tabeli 'orders'");
    }

    @Override
    public Optional<Order> findById(int id) {
        System.out.println("SQL: Pobieranie zamówienia #" + id);
        return Optional.empty();
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>();
    }

    @Override
    public void delete(int id) {
        System.out.println("SQL: Usuwanie zamówienia #" + id);
    }
}