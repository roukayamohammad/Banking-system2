package domain.report;

import java.time.LocalDateTime;

public class AuditLog {
    private String action;
    private LocalDateTime time;

    public AuditLog(String action) {
        this.action = action;
        this.time = LocalDateTime.now();
    }

    public String getAction() { return action; }
    public LocalDateTime getTime() { return time; }
}
