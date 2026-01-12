package Solid.inventory;

public class WarehouseNotifier {
    public void sendDispatchOrder(String orderId, String details) {
        System.out.println("WarehouseNotifier: Wysłano zlecenie wydania dla zamówienia #" + orderId);
    }
}