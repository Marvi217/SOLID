import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.util.Date;

/**
 * =============================================================================
 * LEGACY RETAIL PRO - ROZSZERZONY PRZYKŁAD ANTY-SOLID
 * =============================================================================
 * Ten kod celowo łamie wszystkie zasady SOLID i zawiera liczne anty-wzorce.
 * Służy jako materiał edukacyjny pokazujący, CZEGO NIE ROBIĆ.
 *
 * Złamane zasady:
 * - SRP (Single Responsibility Principle) - klasy robią zbyt wiele
 * - OCP (Open/Closed Principle) - modyfikacje wymagają zmian w istniejącym kodzie
 * - LSP (Liskov Substitution Principle) - podklasy nie zachowują kontraktu
 * - ISP (Interface Segregation Principle) - zbyt duże interfejsy
 * - DIP (Dependency Inversion Principle) - zależność od konkretnych implementacji
 *
 * Dodatkowe anty-wzorce:
 * - God Object
 * - Copy-Paste Programming
 * - Magic Numbers
 * - Hard-coded values
 * - Poor exception handling
 * - Tight coupling
 * =============================================================================
 */

/**
 * INTERFEJS WSZYSTKIEGO (Łamanie ISP)
 * Każdy element systemu musi to implementować, nawet jeśli nie ma sensu.
 */
interface ISystemComponent {
    void process();
    void saveToSql();
    void printToConsole();
    void exportToXml();
    void exportToCsv();
    void exportToJson();
    void exportToPdf();
    void sendEmail(String msg);
    void sendSms(String msg);
    void sendPushNotification(String msg);
    double calculateDiscount();
    void scanPhysicalBarcode();
    void printInvoice();
    void printReceipt();
    void updateInventory();
    void notifyWarehouse();
    void validateData();
    void auditLog();
    void backupData();
    void synchronizeWithCloud();
}

class OrderManager implements ISystemComponent {
    public int orderId;
    public String clientName;
    public String clientEmail;
    public String clientPhone;
    public String clientAddress;
    public List<String> items = new ArrayList<>();
    public List<Double> prices = new ArrayList<>();
    public List<Integer> quantities = new ArrayList<>();
    public double totalAmount = 0;
    public double taxAmount = 0;
    public double discountAmount = 0;
    public String status = "NEW";
    public String paymentMethod = "";
    public Date createdDate = new Date();
    public Date modifiedDate = new Date();
    public String notes = "";
    public boolean isPriority = false;
    public int loyaltyPoints = 0;

    public static int totalOrdersToday = 0;
    public static double totalRevenueToday = 0;
    public static List<String> errorLog = new ArrayList<>();

    public OrderManager(int id, String name) {
        this.orderId = id;
        this.clientName = name;
        totalOrdersToday++;
    }

    public double calculateTotalWithTaxes(String country, String clientType, String season, boolean hasVoucher, String voucherCode) {
        double base = 0;

        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);
            int qty = i < quantities.size() ? quantities.get(i) : 1;

            if (item.equals("Laptop")) base += 3000 * qty;
            else if (item.equals("Laptop Pro")) base += 5000 * qty;
            else if (item.equals("Laptop Gaming")) base += 7000 * qty;
            else if (item.equals("Mouse")) base += 50 * qty;
            else if (item.equals("Mouse Wireless")) base += 80 * qty;
            else if (item.equals("Mouse Gaming")) base += 150 * qty;
            else if (item.equals("Keyboard")) base += 150 * qty;
            else if (item.equals("Keyboard Mechanical")) base += 300 * qty;
            else if (item.equals("Monitor")) base += 1200 * qty;
            else if (item.equals("Monitor 4K")) base += 2500 * qty;
            else if (item.equals("Headphones")) base += 200 * qty;
            else if (item.equals("Webcam")) base += 180 * qty;
            else if (item.equals("USB Cable")) base += 15 * qty;
            else if (item.equals("HDMI Cable")) base += 25 * qty;
            else if (item.equals("Power Bank")) base += 120 * qty;
            else if (item.equals("Phone Case")) base += 40 * qty;
            else if (item.equals("Screen Protector")) base += 20 * qty;
            else if (item.equals("Charger")) base += 35 * qty;
            else if (item.equals("SSD 500GB")) base += 400 * qty;
            else if (item.equals("SSD 1TB")) base += 700 * qty;
            else if (item.equals("RAM 8GB")) base += 250 * qty;
            else if (item.equals("RAM 16GB")) base += 500 * qty;
            else base += 10 * qty;
        }

        double taxRate = 0;
        if (country.equalsIgnoreCase("PL")) {
            taxRate = 0.23;
        } else if (country.equalsIgnoreCase("DE")) {
            taxRate = 0.19;
        } else if (country.equalsIgnoreCase("US")) {
            taxRate = 0.08;
        } else if (country.equalsIgnoreCase("UK")) {
            taxRate = 0.20;
        } else if (country.equalsIgnoreCase("FR")) {
            taxRate = 0.20;
        } else if (country.equalsIgnoreCase("IT")) {
            taxRate = 0.22;
        } else if (country.equalsIgnoreCase("ES")) {
            taxRate = 0.21;
        } else if (country.equalsIgnoreCase("NL")) {
            taxRate = 0.21;
        } else if (country.equalsIgnoreCase("BE")) {
            taxRate = 0.21;
        } else if (country.equalsIgnoreCase("SE")) {
            taxRate = 0.25;
        } else if (country.equalsIgnoreCase("NO")) {
            taxRate = 0.25;
        } else if (country.equalsIgnoreCase("DK")) {
            taxRate = 0.25;
        } else if (country.equalsIgnoreCase("FI")) {
            taxRate = 0.24;
        } else if (country.equalsIgnoreCase("CH")) {
            taxRate = 0.077;
        } else if (country.equalsIgnoreCase("AT")) {
            taxRate = 0.20;
        } else if (country.equalsIgnoreCase("CZ")) {
            taxRate = 0.21;
        } else if (country.equalsIgnoreCase("HU")) {
            taxRate = 0.27;
        } else if (country.equalsIgnoreCase("RO")) {
            taxRate = 0.19;
        } else {
            taxRate = 0.25;
        }

        double discount = 0;
        if (clientType.equals("GOLD")) {
            discount = base * 0.15;
            loyaltyPoints += 150;
        } else if (clientType.equals("SILVER")) {
            discount = base * 0.10;
            loyaltyPoints += 100;
        } else if (clientType.equals("BRONZE")) {
            discount = base * 0.05;
            loyaltyPoints += 50;
        } else if (clientType.equals("PLATINUM")) {
            discount = base * 0.20;
            loyaltyPoints += 200;
        } else if (clientType.equals("DIAMOND")) {
            discount = base * 0.25;
            loyaltyPoints += 300;
        } else if (clientType.equals("STUDENT")) {
            if (base > 500) {
                discount = 150.0;
            } else {
                discount = 50.0;
            }
            loyaltyPoints += 25;
        } else if (clientType.equals("SENIOR")) {
            discount = base * 0.12;
            loyaltyPoints += 80;
        } else if (clientType.equals("EMPLOYEE")) {
            discount = base * 0.30;
            loyaltyPoints += 500;
        } else if (clientType.equals("VIP")) {
            discount = base * 0.35;
            loyaltyPoints += 1000;
        }

        if (season.equals("BLACKFRIDAY")) {
            discount += base * 0.25;
        } else if (season.equals("CHRISTMAS")) {
            discount += base * 0.15;
        } else if (season.equals("SUMMER")) {
            discount += base * 0.10;
        } else if (season.equals("SPRING")) {
            discount += base * 0.08;
        } else if (season.equals("NEWYEAR")) {
            discount += base * 0.20;
        } else if (season.equals("EASTER")) {
            discount += base * 0.12;
        }

        if (hasVoucher) {
            if (voucherCode.equals("WELCOME10")) {
                discount += base * 0.10;
            } else if (voucherCode.equals("SAVE50")) {
                discount += 50;
            } else if (voucherCode.equals("FREESHIP")) {
                discount += 20;
            } else if (voucherCode.equals("VIP100")) {
                discount += 100;
            } else if (voucherCode.equals("MEGA200")) {
                discount += 200;
            }
        }

        if (base > 10000) {
            discount += 500;
        } else if (base > 5000) {
            discount += 250;
        } else if (base > 2000) {
            discount += 100;
        } else if (base > 1000) {
            discount += 50;
        }

        this.taxAmount = base * taxRate;
        this.discountAmount = discount;
        this.totalAmount = base + (base * taxRate) - discount;

        if (this.totalAmount < 0) {
            this.totalAmount = 0;
        }

        totalRevenueToday += this.totalAmount;
        return this.totalAmount;
    }

    @Override
    public void saveToSql() {
        System.out.println("=== Nawiązywanie połączenia z MySQL ===");

        String url = "jdbc:mysql://localhost:3306/legacy_db";
        String user = "admin";
        String pass = "12345";

        try {
            String sql = "INSERT INTO orders (id, client_name, client_email, total, tax, discount, status, created_date) " +
                    "VALUES (" + orderId + ", '" + clientName + "', '" + clientEmail + "', " +
                    totalAmount + ", " + taxAmount + ", " + discountAmount + ", '" + status + "', NOW())";
            System.out.println("SQL: " + sql);

            for (int i = 0; i < items.size(); i++) {
                String itemSql = "INSERT INTO order_items (order_id, item_name, quantity, price) " +
                        "VALUES (" + orderId + ", '" + items.get(i) + "', " +
                        (i < quantities.size() ? quantities.get(i) : 1) + ", " +
                        (i < prices.size() ? prices.get(i) : 0) + ")";
                System.out.println("SQL: " + itemSql);
            }

            FileWriter fw = new FileWriter("C:\\Users\\Dell\\Desktop\\Informatyka\\ProjektowanieObiektowe\\Lab03\\ChessAntySolid\\src\\temp\\order_log.txt", true);
            fw.write("Order saved: " + orderId + " for " + clientName +
                    " at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +
                    " | Total: " + totalAmount + "\n");
            fw.close();

            FileWriter fw2 = new FileWriter("C:\\Users\\Dell\\Desktop\\Informatyka\\ProjektowanieObiektowe\\Lab03\\ChessAntySolid\\src\\buckup\\orders.log", true);
            fw2.write(orderId + "," + clientName + "," + totalAmount + "," +
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "\n");
            fw2.close();

        } catch (Exception e) {
            e.printStackTrace();
            errorLog.add("Error saving order " + orderId + ": " + e.getMessage());
        }
    }

    @Override
    public void sendEmail(String msg) {
        System.out.println("=== Wysyłanie Email ===");
        System.out.println("To: " + clientEmail);
        System.out.println("Subject: Potwierdzenie zamówienia #" + orderId);

        String smtpServer = "smtp.gmail.com";
        String smtpPort = "587";
        String emailUser = "admin@legacy-retail.com";
        String emailPass = "secretpassword123";

        String htmlBody = "<html><body>" +
                "<h1>Dziękujemy za zamówienie!</h1>" +
                "<p>Szanowny " + clientName + ",</p>" +
                "<p>Twoje zamówienie #" + orderId + " zostało przyjęte.</p>" +
                "<p>Wartość: " + totalAmount + " PLN</p>" +
                "<p>Status: " + status + "</p>" +
                "<hr>" +
                "<p style='color: gray; font-size: 10px;'>Legacy Retail Pro v1.0</p>" +
                "</body></html>";

        System.out.println("Connecting to " + smtpServer + ":" + smtpPort);
        System.out.println("Message prepared, length: " + htmlBody.length() + " chars");

        try {
            FileWriter fw = new FileWriter("C:\\Users\\Dell\\Desktop\\Informatyka\\ProjektowanieObiektowe\\Lab03\\ChessAntySolid\\src\\temp\\email_log.txt", true);
            fw.write("Email sent to " + clientEmail + " at " + new Date() + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendSms(String msg) {
        System.out.println("=== Wysyłanie SMS ===");

        String smsGateway = "https://api.smsgateway.com/send";
        String apiKey = "abc123def456";

        if (clientPhone == null || clientPhone.length() < 9) {
            System.out.println("ERROR: Invalid phone number");
            return;
        }

        String smsBody = "Zamowienie #" + orderId + " przyjete. Wartosc: " + totalAmount + " PLN";
        System.out.println("SMS to " + clientPhone + ": " + smsBody);

        try {
            FileWriter fw = new FileWriter("C:\\Users\\Dell\\Desktop\\Informatyka\\ProjektowanieObiektowe\\Lab03\\ChessAntySolid\\src\\temp\\sms_log.txt", true);
            fw.write("SMS sent to " + clientPhone + " at " + new Date() + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendPushNotification(String msg) {
        System.out.println("=== Wysyłanie Push Notification ===");

        String pushApiUrl = "https://onesignal.com/api/v1/notifications";
        String appId = "xyz789";
        String apiKey = "push_secret_key";

        System.out.println("Push notification: " + msg);

        try {
            FileWriter fw = new FileWriter("C:\\Users\\Dell\\Desktop\\Informatyka\\ProjektowanieObiektowe\\Lab03\\ChessAntySolid\\src\\temp\\push_log.txt", true);
            fw.write("Push sent for order " + orderId + " at " + new Date() + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void scanPhysicalBarcode() {
        throw new RuntimeException("CRITICAL ERROR: Physical barcode scanner device not found! System cannot continue!");
    }

    @Override
    public void printInvoice() {
        System.out.println("=== FAKTURA VAT ===");
        System.out.println("Nr: " + orderId + "/" + new SimpleDateFormat("yyyy").format(new Date()));
        System.out.println("Data: " + new SimpleDateFormat("yyyy-MM-dd").format(createdDate));
        System.out.println("Klient: " + clientName);
        System.out.println("Adres: " + clientAddress);
        System.out.println("------------------------");

        for (int i = 0; i < items.size(); i++) {
            int qty = i < quantities.size() ? quantities.get(i) : 1;
            System.out.println(items.get(i) + " x" + qty);
        }

        System.out.println("------------------------");
        System.out.println("Netto: " + (totalAmount - taxAmount) + " PLN");
        System.out.println("VAT: " + taxAmount + " PLN");
        System.out.println("Rabat: " + discountAmount + " PLN");
        System.out.println("RAZEM: " + totalAmount + " PLN");
        System.out.println("========================");

        try {
            FileWriter fw = new FileWriter("C:\\Users\\Dell\\Desktop\\Informatyka\\ProjektowanieObiektowe\\Lab03\\ChessAntySolid\\src\\invoices\\invoice_" + orderId + ".txt");
            fw.write("INVOICE #" + orderId + "\n");
            fw.write("Total: " + totalAmount + " PLN\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printReceipt() {
        System.out.println("=== PARAGON ===");
        System.out.println("Legacy Retail Pro");
        System.out.println("ul. Przykładowa 123");
        System.out.println("00-001 Warszawa");
        System.out.println("NIP: 123-456-78-90");
        System.out.println("------------------------");
        System.out.println("Data: " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(createdDate));
        System.out.println("Paragon: " + orderId);
        System.out.println("------------------------");

        for (int i = 0; i < items.size(); i++) {
            int qty = i < quantities.size() ? quantities.get(i) : 1;
            System.out.println(items.get(i) + " x" + qty);
        }

        System.out.println("------------------------");
        System.out.println("SUMA: " + totalAmount + " PLN");
        System.out.println("========================");
    }

    @Override
    public void updateInventory() {
        System.out.println("=== Aktualizacja magazynu ===");

        for (String item : items) {
            String sql = "UPDATE inventory SET quantity = quantity - 1 WHERE product_name = '" + item + "'";
            System.out.println("SQL: " + sql);
        }
    }

    @Override
    public void notifyWarehouse() {
        System.out.println("=== Powiadomienie magazynu ===");
        System.out.println("Sending notification to warehouse system...");

        String warehouseApi = "http://warehouse.legacy-retail.com/api/notify";
        System.out.println("POST " + warehouseApi);
    }

    @Override
    public void validateData() {
        System.out.println("=== Walidacja danych ===");

        if (clientName == null || clientName.length() < 3) {
            throw new RuntimeException("Invalid client name");
        }
        if (clientEmail == null || !clientEmail.contains("@")) {
            throw new RuntimeException("Invalid email");
        }
        if (items.size() == 0) {
            throw new RuntimeException("No items in order");
        }
        if (totalAmount < 0) {
            throw new RuntimeException("Invalid total amount");
        }

        System.out.println("Validation passed");
    }

    @Override
    public void auditLog() {
        try {
            FileWriter fw = new FileWriter("C:\\Users\\Dell\\Desktop\\Informatyka\\ProjektowanieObiektowe\\Lab03\\ChessAntySolid\\src\\audit\\audit.log", true);
            fw.write("[" + new Date() + "] Order " + orderId + " by " + clientName +
                    " | Total: " + totalAmount + " | Status: " + status + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void backupData() {
        System.out.println("=== Backup danych ===");
        try {
            FileWriter fw = new FileWriter("C:\\backup\\order_" + orderId + ".backup");
            fw.write("ORDER_ID:" + orderId + "\n");
            fw.write("CLIENT:" + clientName + "\n");
            fw.write("TOTAL:" + totalAmount + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void synchronizeWithCloud() {
        System.out.println("=== Synchronizacja z chmurą ===");
        String cloudApi = "https://cloud.legacy-retail.com/sync";
        System.out.println("Syncing to " + cloudApi);
    }

    @Override public void process() { System.out.println("Processing order..."); }
    @Override public void printToConsole() {
        System.out.println("Order #" + orderId + " | Client: " + clientName + " | Total: " + totalAmount);
    }
    @Override public void exportToXml() { System.out.println("<order id='" + orderId + "'>...</order>"); }
    @Override public void exportToCsv() { System.out.println(orderId + "," + clientName + "," + totalAmount); }
    @Override public void exportToJson() { System.out.println("{\"orderId\":" + orderId + "}"); }
    @Override public void exportToPdf() { System.out.println("Generating PDF..."); }
    @Override public double calculateDiscount() { return discountAmount; }
}

class SubscriptionOrder extends OrderManager {
    public String subscriptionPlan;
    public int billingCycleMonths;

    public SubscriptionOrder(int id, String name) {
        super(id, name);
        this.subscriptionPlan = "BASIC";
        this.billingCycleMonths = 1;
    }

    @Override
    public void saveToSql() {
        System.out.println("=== Saving subscription to AWS S3 instead of SQL ===");
        System.out.println("Bucket: subscriptions-backup");
        System.out.println("File: subscription_" + orderId + ".json");
    }

    @Override
    public void scanPhysicalBarcode() {
        System.out.println("Subscriptions don't have physical barcodes!");
    }
}

class WholesaleOrder extends OrderManager {
    public String companyTaxId;
    public int bulkDiscountPercent = 30;

    public WholesaleOrder(int id, String name) {
        super(id, name);
    }

    @Override
    public void sendEmail(String msg) {
        System.out.println("Sending to B2B department instead: b2b@legacy-retail.com");
    }
}

class PaymentProcessor {
    private String stripeApiKey = "sk_test_123456789";
    private String paypalClientId = "paypal_client_xyz";
    private String bitcoinWalletAddress = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa";

    public void pay(String method, double amount, String currency) {
        if (method.equals("CASH")) {
            System.out.println("=== PAYMENT: CASH ===");
            System.out.println("Amount: " + amount + " " + currency);
            System.out.println("Cash collected");

        } else if (method.equals("CARD")) {
            System.out.println("=== PAYMENT: CARD ===");
            System.out.println("Connecting to Stripe API...");
            System.out.println("API Key: " + stripeApiKey);
            System.out.println("Charging " + amount + " " + currency);
            System.out.println("Processing VISA/Mastercard...");

        } else if (method.equals("PAYPAL")) {
            System.out.println("=== PAYMENT: PAYPAL ===");
            System.out.println("Redirecting to PayPal...");
            System.out.println("Client ID: " + paypalClientId);
            System.out.println("Amount: " + amount + " " + currency);

        } else if (method.equals("BITCOIN")) {
            System.out.println("=== PAYMENT: BITCOIN ===");
            System.out.println("Wallet: " + bitcoinWalletAddress);
            System.out.println("Amount: " + amount + " BTC");
            System.out.println("Mining transaction...");
            System.out.println("Waiting for confirmations...");

        } else if (method.equals("BLIK")) {
            System.out.println("=== PAYMENT: BLIK ===");
            System.out.println("Enter 6-digit code...");
            System.out.println("Amount: " + amount + " PLN");

        } else if (method.equals("TRANSFER")) {
            System.out.println("=== PAYMENT: BANK TRANSFER ===");
            System.out.println("Bank account: 12 3456 7890 1234 5678 9012 3456");
            System.out.println("Title: Payment for order");
            System.out.println("Amount: " + amount + " " + currency);

        } else if (method.equals("APPLEPAY")) {
            System.out.println("=== PAYMENT: APPLE PAY ===");
            System.out.println("Connecting to Apple Pay API...");
            System.out.println("Amount: " + amount + " " + currency);

        } else if (method.equals("GOOGLEPAY")) {
            System.out.println("=== PAYMENT: GOOGLE PAY ===");
            System.out.println("Connecting to Google Pay API...");
            System.out.println("Amount: " + amount + " " + currency);

        } else if (method.equals("INSTALLMENTS")) {
            System.out.println("=== PAYMENT: INSTALLMENTS ===");
            int months = 12;
            double monthlyPayment = amount / months;
            System.out.println("Amount: " + amount + " " + currency);
            System.out.println("Months: " + months);
            System.out.println("Monthly: " + monthlyPayment + " " + currency);

        } else if (method.equals("DEFERRED")) {
            System.out.println("=== PAYMENT: DEFERRED (Buy now, pay later) ===");
            System.out.println("Amount: " + amount + " " + currency);
            System.out.println("Pay in 30 days");

        } else {
            System.out.println("ERROR: Unknown payment method: " + method);
            throw new RuntimeException("Payment failed");
        }

        try {
            FileWriter fw = new FileWriter("C:\\Users\\Dell\\Desktop\\Informatyka\\ProjektowanieObiektowe\\Lab03\\ChessAntySolid\\src\\temp\\payments.log", true);
            fw.write("Payment: " + method + " | Amount: " + amount + " " + currency +
                    " | Date: " + new Date() + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ReportGenerator {

    public void generateDailyReport() {
        System.out.println("==========================================");
        System.out.println("       RAPORT DZIENNY - SPRZEDAŻ");
        System.out.println("==========================================");
        System.out.println("Data: " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        System.out.println("------------------------------------------");

        String url = "jdbc:mysql://localhost:3306/legacy_db";

        System.out.println("Liczba zamówień: " + OrderManager.totalOrdersToday);
        System.out.println("Przychód: " + OrderManager.totalRevenueToday + " PLN");
        System.out.println("Średnia wartość: " + (OrderManager.totalRevenueToday / OrderManager.totalOrdersToday) + " PLN");

        System.out.println("\nSprzedaż według kategorii:");
        System.out.println("  - Laptopy: 15 szt.");
        System.out.println("  - Akcesoria: 45 szt.");
        System.out.println("  - Monitory: 8 szt.");

        System.out.println("\nTop 5 produktów:");
        System.out.println("  1. Laptop Pro - 12 szt.");
        System.out.println("  2. Mouse Wireless - 23 szt.");
        System.out.println("  3. Keyboard Mechanical - 18 szt.");
        System.out.println("  4. Monitor 4K - 7 szt.");
        System.out.println("  5. Headphones - 15 szt.");

        System.out.println("\n==========================================");

        try {
            FileWriter fw = new FileWriter("C:\\Users\\Dell\\Desktop\\Informatyka\\ProjektowanieObiektowe\\Lab03\\ChessAntySolid\\src\\reports\\daily_" +
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".txt");
            fw.write("DAILY REPORT\n");
            fw.write("Orders: " + OrderManager.totalOrdersToday + "\n");
            fw.write("Revenue: " + OrderManager.totalRevenueToday + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateWeeklyReport() {
        System.out.println("==========================================");
        System.out.println("      RAPORT TYGODNIOWY - SPRZEDAŻ");
        System.out.println("==========================================");
        System.out.println("Tydzień: " + new SimpleDateFormat("ww/yyyy").format(new Date()));
        System.out.println("------------------------------------------");

        String url = "jdbc:mysql://localhost:3306/legacy_db";

        System.out.println("Liczba zamówień: " + (OrderManager.totalOrdersToday * 7));
        System.out.println("Przychód: " + (OrderManager.totalRevenueToday * 7) + " PLN");
        System.out.println("Średnia dzienna: " + OrderManager.totalRevenueToday + " PLN");

        System.out.println("\nSprzedaż według kategorii:");
        System.out.println("  - Laptopy: 105 szt.");
        System.out.println("  - Akcesoria: 315 szt.");
        System.out.println("  - Monitory: 56 szt.");

        System.out.println("\nTop 5 produktów:");
        System.out.println("  1. Laptop Pro - 84 szt.");
        System.out.println("  2. Mouse Wireless - 161 szt.");
        System.out.println("  3. Keyboard Mechanical - 126 szt.");
        System.out.println("  4. Monitor 4K - 49 szt.");
        System.out.println("  5. Headphones - 105 szt.");

        System.out.println("\n==========================================");

        try {
            FileWriter fw = new FileWriter("C:\\Users\\Dell\\Desktop\\Informatyka\\ProjektowanieObiektowe\\Lab03\\ChessAntySolid\\src\\reports\\weekly_" +
                    new SimpleDateFormat("yyyy-ww").format(new Date()) + ".txt");
            fw.write("WEEKLY REPORT\n");
            fw.write("Orders: " + (OrderManager.totalOrdersToday * 7) + "\n");
            fw.write("Revenue: " + (OrderManager.totalRevenueToday * 7) + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateMonthlyReport() {
        System.out.println("==========================================");
        System.out.println("      RAPORT MIESIĘCZNY - SPRZEDAŻ");
        System.out.println("==========================================");
        System.out.println("Miesiąc: " + new SimpleDateFormat("MM/yyyy").format(new Date()));
        System.out.println("------------------------------------------");

        String url = "jdbc:mysql://localhost:3306/legacy_db";

        System.out.println("Liczba zamówień: " + (OrderManager.totalOrdersToday * 30));
        System.out.println("Przychód: " + (OrderManager.totalRevenueToday * 30) + " PLN");
        System.out.println("Średnia dzienna: " + OrderManager.totalRevenueToday + " PLN");

        System.out.println("\nSprzedaż według kategorii:");
        System.out.println("  - Laptopy: 450 szt.");
        System.out.println("  - Akcesoria: 1350 szt.");
        System.out.println("  - Monitory: 240 szt.");

        System.out.println("\nTop 5 produktów:");
        System.out.println("  1. Laptop Pro - 360 szt.");
        System.out.println("  2. Mouse Wireless - 690 szt.");
        System.out.println("  3. Keyboard Mechanical - 540 szt.");
        System.out.println("  4. Monitor 4K - 210 szt.");
        System.out.println("  5. Headphones - 450 szt.");

        System.out.println("\n==========================================");

        try {
            FileWriter fw = new FileWriter("C:\\Users\\Dell\\Desktop\\Informatyka\\ProjektowanieObiektowe\\Lab03\\ChessAntySolid\\src\\reports\\monthly_" +
                    new SimpleDateFormat("yyyy-MM").format(new Date()) + ".txt");
            fw.write("MONTHLY REPORT\n");
            fw.write("Orders: " + (OrderManager.totalOrdersToday * 30) + "\n");
            fw.write("Revenue: " + (OrderManager.totalRevenueToday * 30) + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateYearlyReport() {
        System.out.println("==========================================");
        System.out.println("       RAPORT ROCZNY - SPRZEDAŻ");
        System.out.println("==========================================");
        System.out.println("Rok: " + new SimpleDateFormat("yyyy").format(new Date()));
        System.out.println("------------------------------------------");

        String url = "jdbc:mysql://localhost:3306/legacy_db";

        System.out.println("Liczba zamówień: " + (OrderManager.totalOrdersToday * 365));
        System.out.println("Przychód: " + (OrderManager.totalRevenueToday * 365) + " PLN");
        System.out.println("Średnia miesięczna: " + (OrderManager.totalRevenueToday * 30) + " PLN");

        System.out.println("\nSprzedaż według kategorii:");
        System.out.println("  - Laptopy: 5475 szt.");
        System.out.println("  - Akcesoria: 16425 szt.");
        System.out.println("  - Monitory: 2920 szt.");

        System.out.println("\nTop 5 produktów:");
        System.out.println("  1. Laptop Pro - 4380 szt.");
        System.out.println("  2. Mouse Wireless - 8395 szt.");
        System.out.println("  3. Keyboard Mechanical - 6570 szt.");
        System.out.println("  4. Monitor 4K - 2555 szt.");
        System.out.println("  5. Headphones - 5475 szt.");

        System.out.println("\n==========================================");

        try {
            FileWriter fw = new FileWriter("C:\\Users\\Dell\\Desktop\\Informatyka\\ProjektowanieObiektowe\\Lab03\\ChessAntySolid\\src\\reports\\yearly_" +
                    new SimpleDateFormat("yyyy").format(new Date()) + ".txt");
            fw.write("YEARLY REPORT\n");
            fw.write("Orders: " + (OrderManager.totalOrdersToday * 365) + "\n");
            fw.write("Revenue: " + (OrderManager.totalRevenueToday * 365) + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateCustomReport(Date from, Date to) {
        System.out.println("==========================================");
        System.out.println("         RAPORT NIESTANDARDOWY");
        System.out.println("==========================================");
        System.out.println("Od: " + new SimpleDateFormat("yyyy-MM-dd").format(from));
        System.out.println("Do: " + new SimpleDateFormat("yyyy-MM-dd").format(to));
        System.out.println("------------------------------------------");

        System.out.println("Analiza w toku...");
        System.out.println("\n==========================================");
    }
}

class CustomerManager {
    public int customerId;
    public String name;
    public String email;
    public String phone;
    public String address;
    public String customerType;
    public int loyaltyPoints = 0;
    public List<Integer> orderHistory = new ArrayList<>();
    public double lifetimeValue = 0;

    public double creditLimit = 5000.0;
    public double currentCredit = 0.0;

    public CustomerManager(int id, String name) {
        this.customerId = id;
        this.name = name;
    }

    public void upgradeTier() {
        if (lifetimeValue > 50000) {
            customerType = "DIAMOND";
            creditLimit = 50000;
        } else if (lifetimeValue > 30000) {
            customerType = "PLATINUM";
            creditLimit = 30000;
        } else if (lifetimeValue > 15000) {
            customerType = "GOLD";
            creditLimit = 15000;
        } else if (lifetimeValue > 7000) {
            customerType = "SILVER";
            creditLimit = 7000;
        } else if (lifetimeValue > 3000) {
            customerType = "BRONZE";
            creditLimit = 3000;
        } else {
            customerType = "STANDARD";
            creditLimit = 1000;
        }

        System.out.println("Customer " + name + " upgraded to " + customerType);
    }

    public void saveToDatabase() {
        String url = "jdbc:mysql://localhost:3306/legacy_db";
        String sql = "INSERT INTO customers (id, name, email, type, points, lifetime_value) " +
                "VALUES (" + customerId + ", '" + name + "', '" + email + "', '" +
                customerType + "', " + loyaltyPoints + ", " + lifetimeValue + ")";
        System.out.println("SQL: " + sql);
    }

    public void sendNewsletter() {
        System.out.println("Sending newsletter to " + email);
        String htmlContent = "<html><body><h1>Newsletter</h1><p>Oferty specjalne!</p></body></html>";
    }

    public void calculateStatistics() {
        int totalOrders = orderHistory.size();
        double avgOrderValue = totalOrders > 0 ? lifetimeValue / totalOrders : 0;
        System.out.println("Customer stats: " + totalOrders + " orders, avg " + avgOrderValue + " PLN");
    }
}

class InventoryManager {
    public static Map<String, Integer> stock = new HashMap<>();
    public static Map<String, Double> prices = new HashMap<>();
    public static Map<String, String> suppliers = new HashMap<>();

    static {
        stock.put("Laptop", 50);
        stock.put("Laptop Pro", 30);
        stock.put("Mouse", 200);
        stock.put("Keyboard", 150);
        stock.put("Monitor", 40);

        prices.put("Laptop", 3000.0);
        prices.put("Laptop Pro", 5000.0);
        prices.put("Mouse", 50.0);
        prices.put("Keyboard", 150.0);
        prices.put("Monitor", 1200.0);

        suppliers.put("Laptop", "TechSupplier Corp");
        suppliers.put("Mouse", "PeripheralsInc");
    }

    public static void checkStockAndReorder(String product) {
        Integer currentStock = stock.get(product);

        if (currentStock == null) {
            System.out.println("ERROR: Product not found: " + product);
            return;
        }

        if (currentStock < 10) {
            System.out.println("LOW STOCK WARNING: " + product + " = " + currentStock);

            String supplier = suppliers.get(product);
            if (supplier != null) {
                System.out.println("Auto-ordering from " + supplier);
                System.out.println("Order: 100 units of " + product);

                String supplierApi = "http://supplier-api.com/order";
                System.out.println("POST " + supplierApi);

                stock.put(product, currentStock + 100);
            }
        }

        try {
            FileWriter fw = new FileWriter("C:\\Users\\Dell\\Desktop\\Informatyka\\ProjektowanieObiektowe\\Lab03\\ChessAntySolid\\src\\temp\\inventory_log.txt", true);
            fw.write("Checked stock for " + product + ": " + currentStock + " at " + new Date() + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateInventoryReport() {
        System.out.println("=== RAPORT INWENTARZA ===");
        System.out.println("Data: " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        System.out.println("-------------------------");

        double totalValue = 0;
        for (String product : stock.keySet()) {
            int qty = stock.get(product);
            double price = prices.getOrDefault(product, 0.0);
            double value = qty * price;
            totalValue += value;

            System.out.println(product + ": " + qty + " szt. (wartość: " + value + " PLN)");
        }

        System.out.println("-------------------------");
        System.out.println("WARTOŚĆ CAŁKOWITA: " + totalValue + " PLN");
        System.out.println("=========================");
    }
}

class LoggingSystem {
    private static String logFile = "C:\\Users\\Dell\\Desktop\\Informatyka\\ProjektowanieObiektowe\\Lab03\\ChessAntySolid\\src\\temp\\system.log";

    public static void logError(String message) {
        try {
            FileWriter fw = new FileWriter(logFile, true);
            fw.write("[ERROR] " + new Date() + " - " + message + "\n");
            fw.close();
        } catch (Exception e) {
            System.err.println("Cannot write to log file!");
        }
    }

    public static void logInfo(String message) {
        try {
            FileWriter fw = new FileWriter(logFile, true);
            fw.write("[INFO] " + new Date() + " - " + message + "\n");
            fw.close();
        } catch (Exception e) {
            System.err.println("Cannot write to log file!");
        }
    }

    public static void logWarning(String message) {
        try {
            FileWriter fw = new FileWriter(logFile, true);
            fw.write("[WARNING] " + new Date() + " - " + message + "\n");
            fw.close();
        } catch (Exception e) {
            System.err.println("Cannot write to log file!");
        }
    }

    public static void clearAllLogs() {
        try {
            FileWriter fw = new FileWriter(logFile, false);
            fw.write("");
            fw.close();
            System.out.println("All logs cleared!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class LegacyRetailPro {

    public static void main(String[] args) {

        LoggingSystem.logInfo("System starting...");

        try {
            System.out.println("\n[SCENARIUSZ 1] Tworzenie standardowego zamówienia");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            OrderManager order1 = new OrderManager(101, "Janusz Biznesu");
            order1.clientEmail = "janusz@firma.pl";
            order1.clientPhone = "+48123456789";
            order1.clientAddress = "ul. Kwiatowa 15, 00-001 Warszawa";

            order1.items.add("Laptop");
            order1.items.add("Mouse");
            order1.items.add("Keyboard");
            order1.quantities.add(1);
            order1.quantities.add(2);
            order1.quantities.add(1);

            double total1 = order1.calculateTotalWithTaxes("PL", "GOLD", "NONE", false, "");
            System.out.println("\nWyliczona kwota: " + total1 + " PLN");

            order1.validateData();
            order1.printToConsole();
            order1.saveToSql();
            order1.printInvoice();
            order1.sendEmail("Potwierdzenie zamówienia");
            order1.updateInventory();
            order1.auditLog();

            PaymentProcessor pp1 = new PaymentProcessor();
            pp1.pay("CARD", total1, "PLN");

            LoggingSystem.logInfo("Order " + order1.orderId + " completed successfully");

            System.out.println("\n\n[SCENARIUSZ 2] Zamówienie subskrypcyjne (LSP violation)");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            SubscriptionOrder sub = new SubscriptionOrder(202, "Anna Nowak");
            sub.clientEmail = "anna@email.com";
            sub.items.add("Premium Subscription");
            sub.subscriptionPlan = "PREMIUM";

            double total2 = sub.calculateTotalWithTaxes("PL", "STANDARD", "NONE", false, "");
            System.out.println("\nWyliczona kwota subskrypcji: " + total2 + " PLN/miesiąc");

            handleSaving(sub);

            PaymentProcessor pp2 = new PaymentProcessor();
            pp2.pay("CARD", total2, "PLN");

            System.out.println("\n\n[SCENARIUSZ 3] Zamówienie hurtowe B2B");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            WholesaleOrder wholesale = new WholesaleOrder(303, "TechCorp Sp. z o.o.");
            wholesale.clientEmail = "orders@techcorp.pl";
            wholesale.companyTaxId = "PL1234567890";
            wholesale.items.add("Laptop Pro");
            wholesale.items.add("Monitor 4K");
            wholesale.quantities.add(50);
            wholesale.quantities.add(30);

            double total3 = wholesale.calculateTotalWithTaxes("PL", "VIP", "NONE", false, "");
            System.out.println("\nWartość zamówienia hurtowego: " + total3 + " PLN");

            wholesale.saveToSql();
            wholesale.sendEmail("Potwierdzenie B2B");

            PaymentProcessor pp3 = new PaymentProcessor();
            pp3.pay("TRANSFER", total3, "PLN");

            System.out.println("\n\n[SCENARIUSZ 4] Zamówienie z wieloma promocjami");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            OrderManager order4 = new OrderManager(404, "Krzysztof Kowalski");
            order4.clientEmail = "krzysiek@gmail.com";
            order4.clientPhone = "+48987654321";
            order4.items.add("Laptop Gaming");
            order4.items.add("Mouse Gaming");
            order4.items.add("Keyboard Mechanical");
            order4.items.add("Headphones");
            order4.quantities.add(1);
            order4.quantities.add(1);
            order4.quantities.add(1);
            order4.quantities.add(1);

            double total4 = order4.calculateTotalWithTaxes("PL", "PLATINUM", "BLACKFRIDAY", true, "MEGA200");
            System.out.println("\nKwota po wszystkich zniżkach: " + total4 + " PLN");
            System.out.println("Zniżka: " + order4.discountAmount + " PLN");
            System.out.println("Podatek: " + order4.taxAmount + " PLN");

            order4.saveToSql();
            order4.printReceipt();
            order4.sendEmail("Dziękujemy za zakup!");
            order4.sendSms("Zamowienie przyjete");
            order4.sendPushNotification("Twoje zamowienie jest w realizacji");

            PaymentProcessor pp4 = new PaymentProcessor();
            pp4.pay("PAYPAL", total4, "PLN");


            System.out.println("\n\n[SCENARIUSZ 5] Demo różnych metod płatności");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            PaymentProcessor pp = new PaymentProcessor();
            System.out.println("\n--- Test płatności gotówką ---");
            pp.pay("CASH", 500.0, "PLN");

            System.out.println("\n--- Test płatności BLIK ---");
            pp.pay("BLIK", 350.0, "PLN");

            System.out.println("\n--- Test płatności Bitcoin ---");
            pp.pay("BITCOIN", 0.015, "BTC");

            System.out.println("\n--- Test ratalnej ---");
            pp.pay("INSTALLMENTS", 6000.0, "PLN");


            System.out.println("\n\n[SCENARIUSZ 6] System zarządzania klientami");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            CustomerManager customer = new CustomerManager(1001, "Maria Wiśniewska");
            customer.email = "maria@example.com";
            customer.phone = "+48111222333";
            customer.address = "ul. Długa 45, 31-000 Kraków";
            customer.lifetimeValue = 12500.0;
            customer.loyaltyPoints = 850;
            customer.orderHistory.add(101);
            customer.orderHistory.add(202);
            customer.orderHistory.add(404);

            System.out.println("Klient: " + customer.name);
            System.out.println("Wartość życiowa: " + customer.lifetimeValue + " PLN");
            System.out.println("Punkty lojalnościowe: " + customer.loyaltyPoints);

            customer.upgradeTier();
            customer.saveToDatabase();
            customer.calculateStatistics();
            customer.sendNewsletter();

            System.out.println("\n\n[SCENARIUSZ 7] System zarządzania magazynem");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            System.out.println("\nSprawdzanie stanu magazynowego:");
            InventoryManager.checkStockAndReorder("Laptop");
            InventoryManager.checkStockAndReorder("Mouse");

            InventoryManager.stock.put("Monitor", 5);
            InventoryManager.checkStockAndReorder("Monitor");

            System.out.println("\nGenerowanie raportu inwentarza:");
            InventoryManager.generateInventoryReport();

            System.out.println("\n\n[SCENARIUSZ 8] Generowanie raportów sprzedaży");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            ReportGenerator reporter = new ReportGenerator();

            System.out.println("\n--- Raport dzienny ---");
            reporter.generateDailyReport();

            System.out.println("\n--- Raport tygodniowy ---");
            reporter.generateWeeklyReport();

            System.out.println("\n--- Raport miesięczny ---");
            reporter.generateMonthlyReport();

            System.out.println("\n\n[SCENARIUSZ 9] Demonstracja złej obsługi błędów");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            try {
                OrderManager badOrder = new OrderManager(999, "Test User");
                badOrder.items.add("Laptop");

                System.out.println("\nPróba skanowania kodu kreskowego dla zamówienia cyfrowego...");
                badOrder.scanPhysicalBarcode();

            } catch (RuntimeException e) {
                System.out.println("Ups! Wystąpił błąd: " + e.getMessage());
                System.out.println("Ale program działa dalej... (to źle!)");
                LoggingSystem.logError(e.getMessage());
            }

            System.out.println("\n\n[SCENARIUSZ 10] Podsumowanie dnia");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            System.out.println("\n╔════════════════════════════════════════════════╗");
            System.out.println("║          STATYSTYKI DZISIEJSZEJ SPRZEDAŻY      ║");
            System.out.println("╠════════════════════════════════════════════════╣");
            System.out.println("║  Liczba zamówień:  " + String.format("%-28s", OrderManager.totalOrdersToday) + "║");
            System.out.println("║  Łączny przychód:  " + String.format("%-19s", String.format("%.2f PLN", OrderManager.totalRevenueToday)) + "║");

            double avgOrderValue = OrderManager.totalOrdersToday > 0
                    ? OrderManager.totalRevenueToday / OrderManager.totalOrdersToday
                    : 0;
            System.out.println("║  Średnia wartość:  " + String.format("%-19s", String.format("%.2f PLN", avgOrderValue)) + "║");
            System.out.println("╚════════════════════════════════════════════════╝");

            if (!OrderManager.errorLog.isEmpty()) {
                System.out.println("\n⚠ UWAGA: Wystąpiły następujące błędy:");
                for (String error : OrderManager.errorLog) {
                    System.out.println("  • " + error);
                }
            }

        } catch (Exception e) {
            System.err.println("\nKRYTYCZNY BŁĄD SYSTEMU: " + e.getMessage());
            e.printStackTrace();
            LoggingSystem.logError("Critical system error: " + e.getMessage());
        }

        System.out.println("\n\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                  SYSTEM ZAKOŃCZYŁ PRACĘ                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        LoggingSystem.logInfo("System shutting down...");


    }
    public static void handleSaving(OrderManager order) {
        System.out.println("\nWywołanie polimorficzne handleSaving()...");
        System.out.println("Programista myśli, że zapisuje do SQL, ale...");
        order.saveToSql();
    }
}
