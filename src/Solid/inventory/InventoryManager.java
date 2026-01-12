package Solid.inventory;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager implements InventoryProvider {
    private final Map<String, Integer> stock = new HashMap<>();

    @Override
    public int getStockLevel(String productName) {
        return stock.getOrDefault(productName, 0);
    }

    @Override
    public boolean hasStock(String productName, int quantity) {
        return getStockLevel(productName) >= quantity;
    }

    @Override
    public void decreaseStock(String productName, int quantity) {
        if (!hasStock(productName, quantity)) {
            throw new IllegalStateException("Błąd magazynowy: Brak towaru " + productName);
        }
        stock.put(productName, getStockLevel(productName) - quantity);
        System.out.println("Magazyn: Zmniejszono stan " + productName + " o " + quantity);
    }

    @Override
    public void increaseStock(String productName, int quantity) {
        stock.put(productName, getStockLevel(productName) + quantity);
    }
}