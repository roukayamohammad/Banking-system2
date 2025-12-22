package domain.report;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class ReportExporter {

    public static void exportDailyTransactions(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {

            writer.write("Daily Transactions Report - " + LocalDate.now() + "\n");
            writer.write("====================================\n");

            for (Transaction tx : TransactionLog.getAll()) {
                writer.write(
                        tx.getTime() + " | " +
                                tx.getAccountId() + " | " +
                                tx.getType() + " | " +
                                tx.getAmount() + "\n"
                );
            }

            System.out.println("📁 Report exported to " + fileName);

        } catch (IOException e) {
            throw new RuntimeException("Failed to export report");
        }
    }
}
