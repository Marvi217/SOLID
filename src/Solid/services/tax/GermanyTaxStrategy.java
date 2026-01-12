package Solid.services.tax;

public class GermanyTaxStrategy implements TaxStrategy {
    @Override
    public double calculateTax(double baseAmount) {
        return baseAmount * 0.19;
    }

    @Override
    public String getCountryCode() { return "DE"; }
}
