package Solid.services.tax;

import java.util.HashMap;
import java.util.Map;

public class TaxStrategyFactory {
    private final Map<String, TaxStrategy> strategies = new HashMap<>();

    public TaxStrategyFactory() {
        strategies.put("PL", new PolandTaxStrategy());
        strategies.put("DE", new GermanyTaxStrategy());
        strategies.put("US", new UsaTaxStrategy());
    }

    public TaxStrategy getStrategy(String countryCode) {
        TaxStrategy strategy = strategies.get(countryCode.toUpperCase());

        if (strategy == null) {
            throw new IllegalArgumentException("Błąd: Nieobsługiwany kraj podatkowy: " + countryCode);
        }

        return strategy;
    }
}