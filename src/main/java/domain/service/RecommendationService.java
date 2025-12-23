package domain.service ;

import domain.entities.Account;
import java.util.List;
import domain.report.Transaction;
import domain.report.TransactionLog;
import java.util.stream.Collectors;

public class RecommendationService {

    public static void analyzeAndRecommend(Account account) {
        System.out.println("\n --- banking recommendations ---");


        analyzeBalance(account);


        analyzeTransactionHistory(account);

        System.out.println("-----------------------------------");
    }

    private static void analyzeBalance(Account account) {
        double balance = account.getBalance();
        String type = account.getClass().getSimpleName();

        if (balance > 25000 && !type.contains("Investment")) {
            System.out.println(" Investment Opportunity: You have a healthy balance of $" + balance + ".");
            System.out.println("   -> Recommendation: Open an 'Investment Account' to earn 4% interest.");
        }
        else if (balance < 100 && balance >= 0) {
            System.out.println(" Low Balance Warning: You are running low on funds.");
            System.out.println("   -> Recommendation: Reduce withdrawals or deposit funds to avoid fees.");
        }
        else if (balance < 0) {
            System.out.println(" Debt Alert: Your account is negative.");
            System.out.println("   -> Recommendation: Consider requesting a 'Loan' to restructure your debt.");
        }
    }

    private static void analyzeTransactionHistory(Account account) {

        List<Transaction> history = TransactionLog.getAll().stream()
                .filter(t -> t.getAccountId().equals(account.getAccountId()))
                .collect(Collectors.toList());

        if (history.isEmpty()) {
            System.out.println(" No transaction history found to analyze.");
            return;
        }


        long withdrawCount = history.stream().filter(t -> t.getType().equals("WITHDRAW")).count();
        long depositCount = history.stream().filter(t -> t.getType().equals("DEPOSIT")).count();


        double totalWithdrawn = history.stream()
                .filter(t -> t.getType().equals("WITHDRAW"))
                .mapToDouble(Transaction::getAmount).sum();


        if (withdrawCount > depositCount * 2) {
            System.out.println("📉 Spending Pattern: Frequent withdrawals detected (" + withdrawCount + " times).");
            System.out.println("   -> Recommendation: Use our 'Budgeting Tool' feature (Coming Soon).");
        }

        if (totalWithdrawn > 5000) {
            System.out.println(" Security Tip: High outflow detected ($" + totalWithdrawn + ").");
            System.out.println("   -> Recommendation: Add 'Insurance Decorator' for fraud protection (Menu Option 9).");
        }
    }
}