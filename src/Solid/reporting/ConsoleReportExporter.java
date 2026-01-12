package Solid.reporting;

public class ConsoleReportExporter implements ReportExporter {
    @Override
    public void export(String content, String destination) {
        System.out.println("--- WYDRUK RAPORTU [" + destination + "] ---");
        System.out.println(content);
    }
}
