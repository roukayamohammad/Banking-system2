package domain.model;

import domain.entities.Account;

public class SavingsAccount extends Account {

    private double interestRate;

    public SavingsAccount(String accountId, String ownerId, double balance) {
        this(accountId, ownerId, balance, 0.02); // قيمة افتراضية 2%
    }
    public SavingsAccount(String accountId, String ownerId, double balance, double interestRate) {
        super(accountId, ownerId, balance);
        if (interestRate < 0 || interestRate > 0.1) { // تأكد من قيمة معقولة
            throw new IllegalArgumentException("Interest rate must be between 0 and 10%");
        }
        this.interestRate = 0.02;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        if (interestRate < 0 || interestRate > 0.1) { // تأكد من قيمة معقولة
            throw new IllegalArgumentException("Interest rate must be between 0 and 10%");
        }
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [Savings, Interest: %.2f%%]",
                interestRate * 100);
    }

}

