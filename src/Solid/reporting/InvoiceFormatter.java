package Solid.reporting;

import Solid.core.Invoice;
import Solid.core.Order;
import Solid.core.OrderItem;

public class InvoiceFormatter implements ReportFormatter<Invoice> {

    public String format(Invoice invoice) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("   FAKTURA NR: ").append(invoice.getInvoiceNumber()).append("\n");
        sb.append("   Data wystawienia: ").append(invoice.getIssueDate()).append("\n");
        sb.append("========================================\n");
        sb.append("Nabywca: ").append(invoice.getOrder().getCustomer().getName()).append("\n");
        sb.append("Pozycje:\n");

        for (OrderItem item : invoice.getOrder().getItems()) {
            sb.append("- ").append(item.getName())
                    .append(" | ").append(item.getQuantity()).append(" szt. | ")
                    .append(item.getPrice()).append(" PLN\n");
        }

        sb.append("----------------------------------------\n");
        sb.append("RAZEM DO ZAPŁATY: ").append(invoice.getFinalAmount()).append(" PLN\n");
        sb.append("========================================\n");
        return sb.toString();
    }
}