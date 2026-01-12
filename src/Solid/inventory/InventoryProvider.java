package Solid.inventory;

public interface InventoryProvider {
    int getStockLevel(String productName);
    boolean hasStock(String productName, int quantity);
    void decreaseStock(String productName, int quantity);
    void increaseStock(String productName, int quantity);
}