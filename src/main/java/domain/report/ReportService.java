package domain.report;

import domain.entities.Customer;
import domain.entities.MockDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReportService {

    public static void dailyTransactions() {
        LocalDate today = LocalDate.now();

        System.out.println("📊 DAILY TRANSACTIONS REPORT");
        System.out.println("📊 Date: " + today.format(DateTimeFormatter.ISO_DATE));

        TransactionLog.getAll().stream()
                .filter(tx -> tx.getTime().toLocalDate().equals(LocalDate.now()))
                .forEach(tx ->
                        System.out.printf("%s | %s | %.2f%n",
                                tx.getAccountId(),
                                tx.getType(),
                                tx.getAmount())
                );
        System.out.println("📈 =================================\n");
    }

    public static void accountSummary() {
        System.out.println("\n📈 =================================");
        System.out.println("📈 ACCOUNT SUMMARY REPORT");
        System.out.println("📈 =================================");


        for (Customer c : MockDatabase.getAllCustomers()) {
            System.out.println("Customer: " + c.getName());
            c.getAccounts().forEach(acc ->

                    System.out.printf("ID: - %s | Balance: %.2f%n | Type: %s | Status: %s %n",
                            acc.getAccountId(), acc.getBalance(), acc.getClass().getSimpleName(), acc.getStateName())

            );
            System.out.println();
        }
        System.out.println("📊 =================================\n");

    }

    public static void auditLogs() {

        System.out.println("\n🔍 =================================");
        System.out.println("🔍 AUDIT LOG");
        System.out.println("🔍 Generated: " + LocalDate.now());
        System.out.println("🔍 =================================");

        AuditService.getLogs().forEach(log ->
                System.out.println(log.getTime() + " -> " + log.getAction())
        );
    }
}
