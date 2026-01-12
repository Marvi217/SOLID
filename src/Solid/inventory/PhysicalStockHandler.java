package Solid.inventory;

public class PhysicalStockHandler implements BarcodeScanner {

    @Override
    public String scan() {
        System.out.println("[Sprzęt] Skaner kodów kreskowych gotowy... BEEP!");
        return "5901234567890";
    }
}