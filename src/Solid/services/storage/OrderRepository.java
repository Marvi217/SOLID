package Solid.services.storage;

import Solid.core.Order;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    void save(Order order);
    Optional<Order> findById(int id);
    List<Order> findAll();
    void delete(int id);
}
