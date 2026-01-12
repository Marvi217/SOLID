package Solid.services.tax;

public class PolandTaxStrategy implements TaxStrategy {
    @Override
    public double calculateTax(double baseAmount) {
        return baseAmount * 0.23;
    }

    @Override
    public String getCountryCode() { return "PL"; }
}

