package domain.model;

import domain.entities.Account;
import domain.strategy.LoanInterestStrategy;

public class LoanAccount extends Account {

    private final double originalLoanAmount;
    private final int loanTermMonths;
    private int monthsPaid;

    public LoanAccount(String accountId, String ownerId, double loanAmount) {
        super(accountId, ownerId, -loanAmount);
        this.originalLoanAmount = loanAmount;
        this.loanTermMonths = 60;
        this.monthsPaid = 0;
        // Strategy
        this.interestStrategy = new LoanInterestStrategy(0.05);
    }

    public void makePayment(double amount) {
        super.deposit(amount);
        monthsPaid++;

        System.out.printf("Loan payment: $%.2f | Remaining: $%.2f%n",
                amount, -balance);

        // تطبيق الفائدة عبر Strategy
        applyInterest();
    }

    public double getMonthlyPayment() {
        LoanInterestStrategy strategy =
                (LoanInterestStrategy) interestStrategy;

        double monthlyRate = strategy.getAnnualRate() / 12;

        return originalLoanAmount *
                (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths)) /
                (Math.pow(1 + monthlyRate, loanTermMonths) - 1);
    }

    public int getRemainingMonths() {
        return loanTermMonths - monthsPaid;
    }

    @Override
    public String toString() {
        return super.toString() +
                String.format(" [Loan, Remaining: %d months]", getRemainingMonths());
    }
}
