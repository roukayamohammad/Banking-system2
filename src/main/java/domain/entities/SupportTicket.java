package domain.entities;

import java.time.LocalDateTime;

public class SupportTicket {
    private static int counter = 1;
    private int id;
    private String customerId;
    private String message;
    private String status;
    private LocalDateTime timestamp;

    public SupportTicket(String customerId, String message) {
        this.id = counter++;
        this.customerId = customerId;
        this.message = message;
        this.status = "Open";
        this.timestamp = LocalDateTime.now();
    }

    public void closeTicket() {
        this.status = "Closed";
    }

    @Override
    public String toString() {
        return String.format("🎫 Ticket #%d [%s] - Customer: %s | Issue: %s | Time: %s",
                id, status, customerId, message, timestamp);
    }
}