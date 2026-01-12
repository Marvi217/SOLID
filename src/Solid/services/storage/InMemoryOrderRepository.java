package Solid.services.storage;

import Solid.core.Order;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<Integer, Order> database = new HashMap<>();

    @Override
    public void save(Order order) {
        database.put(order.getId(), order);
    }

    @Override
    public Optional<Order> findById(int id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public void delete(int id) {
        database.remove(id);
    }
}