package Solid.inventory;

public class PhysicalScanner implements BarcodeScanner {
    @Override
    public String scan() {
        return "EAN-13-890213";
    }
}
