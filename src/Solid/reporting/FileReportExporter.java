package Solid.reporting;

import java.io.FileWriter;
import java.io.IOException;

public class FileReportExporter implements ReportExporter {
    @Override
    public void export(String content, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
            System.out.println("Raport zapisany w: " + filePath);
        } catch (IOException e) {
            System.err.println("Błąd zapisu raportu: " + e.getMessage());
        }
    }
}

