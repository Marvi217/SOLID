package Solid.reporting;

import Solid.core.Order;
import java.io.PrintWriter;

public class DbInvoiceArchiver {
    public void archive(Order order, String content) {
        System.out.println("Faktura dla zamówienia " + order.getId() + " zapisana w archiwum cyfrowym.");
    }
}