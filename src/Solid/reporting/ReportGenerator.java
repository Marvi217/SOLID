package Solid.reporting;

import Solid.core.Order;

public class ReportGenerator {
    private final ReportFormatter formatter;
    private final ReportExporter exporter;

    public ReportGenerator(ReportFormatter formatter, ReportExporter exporter) {
        this.formatter = formatter;
        this.exporter = exporter;
    }

    public void generateReport(Order order, String destination) {
        String content = formatter.format(order);
        exporter.export(content, destination);
    }
}