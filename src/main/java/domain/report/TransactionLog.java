package domain.report;

import java.util.ArrayList;
import java.util.List;

public class TransactionLog {

    private static final List<Transaction> transactions = new ArrayList<>();

    public static void log(Transaction tx) {
        transactions.add(tx);
    }

    public static List<Transaction> getAll() {
        return transactions;
    }
}
