package domain.report;

import java.time.LocalDateTime;

public class Transaction {
    private String accountId;
    private String type;
    private double amount;
    private LocalDateTime time;

    public Transaction(String accountId, String type, double amount) {
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.time = LocalDateTime.now();
    }

    public String getAccountId() { return accountId; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDateTime getTime() { return time; }
}
