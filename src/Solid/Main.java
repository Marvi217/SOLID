package Solid;

import Solid.core.*;
import Solid.inventory.*;
import Solid.logging.*;
import Solid.reporting.*;
import Solid.services.storage.*;
import Solid.services.discount.*;
import Solid.services.notification.*;
import Solid.services.tax.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // --- 1. INICJALIZACJA INFRASTRUKTURY (Logging & Storage) ---
        // Używamy MultiLoggera, by logować jednocześnie do konsoli i pliku (SRP/OCP)
        Logger logger = new MultiLogger(Arrays.asList(
                new ConsoleLogger(),
                new FileLogger("system_audit.log")
        ));

        OrderRepository repository = new InMemoryOrderRepository();
        logger.info("System zainicjalizowany. Baza danych i logowanie gotowe.");

        // --- 2. KONFIGURACJA LOGIKI BIZNESOWEJ (Tax & Discounts) ---
        TaxStrategyFactory taxFactory = new TaxStrategyFactory();
        // Wybieramy strategię dla Polski (OCP)
        TaxStrategy plTax = taxFactory.getStrategy("PL");

        DiscountCalculator discountCalculator = new DiscountCalculator();
        discountCalculator.addStrategy(new VIPCustomerDiscount()); // Rabat dla VIP
        discountCalculator.addStrategy(new HighValueOrderDiscount()); // Rabat za dużą kwotę
        discountCalculator.addStrategy(new BlackFridayDiscount()); // Specjalna promocja

        // --- 3. KONFIGURACJA KOMUNIKACJI I MAGAZYNU ---
        NotificationDispatcher notifications = new NotificationDispatcher();
        notifications.addChannel(new EmailNotificationService());
        notifications.addChannel(new SmsNotificationService());
        notifications.addChannel(new PushNotificationService());

        InventoryManager inventory = new InventoryManager();
        BarcodeScanner scanner = new PhysicalStockHandler();

        // Symulacja dostawy towaru
        inventory.increaseStock("Laptop Gamingowy", 5);
        logger.info("Dostawa przyjęta. Stan magazynowy: " + inventory.getStockLevel("Laptop Gamingowy"));

        // --- 4. PROCES ZAMÓWIENIA (Testowanie Core) ---
        Customer vipCustomer = new Customer(1, "Adam Nowak");
        // Symulacja historii klienta, by stał się DIAMOND (LSP)
        vipCustomer.addOrderToHistory(99, 60000.0);

        Order order = new Order(2024, vipCustomer);

        // Skanowanie fizycznego produktu (wykorzystanie BarcodeScanner)
        String barcode = scanner.scan();
        logger.info("Zeskanowano produkt o kodzie: " + barcode);

        order.addItem(new OrderItem("Laptop Gamingowy", 4000.0, 1));

        // --- 5. OBLICZENIA I FINALIZACJA ---
        // Sprawdzenie dostępności w magazynie
        if (inventory.hasStock("Laptop Gamingowy", 1)) {

            // Obliczanie rabatów i podatków
            double discount = discountCalculator.calculateTotalDiscount(order);
            double total = order.calculateFinalAmount(plTax, discount);

            logger.info("Obliczono kwotę: " + total + " PLN (Rabat: " + discount + " PLN)");

            // Zapis do bazy i aktualizacja magazynu
            repository.save(order);
            inventory.decreaseStock("Laptop Gamingowy", 1);

            // --- 6. RAPORTOWANIE I POWIADOMIENIA ---
            // Generowanie faktury tekstowej na konsolę
            ReportGenerator consoleReport = new ReportGenerator(
                    new InvoiceFormatter(),
                    new ConsoleReportExporter()
            );
            consoleReport.generateReport(order, "EKRAN");

            // Generowanie raportu do pliku (zastępuje stare exportToPdf/Csv)
            ReportGenerator fileReport = new ReportGenerator(
                    new InvoiceFormatter(),
                    new FileReportExporter()
            );
            fileReport.generateReport(order, "faktura_2024.txt");

            // Powiadomienie klienta wszystkimi kanałami (DIP)
            notifications.dispatch(vipCustomer.getEmail(), "Zamówienie #" + order.getId() + " zrealizowane.");

            // Powiadomienie magazynu (SRP)
            WarehouseNotifier warehouseNotifier = new WarehouseNotifier();
            warehouseNotifier.sendDispatchOrder(String.valueOf(order.getId()), "Laptop Gamingowy x1");

        } else {
            logger.error("Brak towaru w magazynie!");
        }

        logger.info("Test systemu SOLID zakończony sukcesem.");
    }
}