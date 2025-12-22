package domain.report;

import java.util.ArrayList;
import java.util.List;

public class AuditService {
    private static final List<AuditLog> logs = new ArrayList<>();

    public static void log(String action) {
        logs.add(new AuditLog(action));
    }

    public static List<AuditLog> getLogs() {
        return logs;
    }
}
