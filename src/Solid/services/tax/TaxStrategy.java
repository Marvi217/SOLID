package Solid.services.tax;

public interface TaxStrategy {
    double calculateTax(double baseAmount);
    String getCountryCode();
}