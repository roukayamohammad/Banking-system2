package domain.model;

import domain.entities.Account;

public class InvestmentAccount extends Account {
    private double annualReturnRate;

    public InvestmentAccount(String accountId, String ownerId, double balance) {
        this(accountId, ownerId, balance, 0.04); // Default 4%
    }

    public InvestmentAccount(String accountId, String ownerId, double balance, double returnRate) {
        super(accountId, ownerId, balance);
        this.annualReturnRate = returnRate;
    }

    public void applyAnnualReturn() {
        double returnAmount = balance * annualReturnRate;
        balance += returnAmount;

        if (returnAmount >= 0) {
            System.out.printf("Investment gain: $%.2f (Rate: %.2f%%) for account %s%n",
                    returnAmount, annualReturnRate * 100, accountId);
        } else {
            System.out.printf("Investment loss: $%.2f (Rate: %.2f%%) for account %s%n",
                    -returnAmount, annualReturnRate * 100, accountId);
        }
    }

    public double getAnnualReturnRate() {
        return annualReturnRate;
    }

    public void setAnnualReturnRate(double rate) {
        this.annualReturnRate = rate;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [Investment, Return Rate: %.2f%%]",
                annualReturnRate * 100);
    }
}
