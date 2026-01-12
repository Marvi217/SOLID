package Solid.services.tax;

public class UsaTaxStrategy implements TaxStrategy {
    @Override
    public double calculateTax(double baseAmount) {
        return baseAmount * 0.08;
    }

    @Override
    public String getCountryCode() { return "US"; }
}
