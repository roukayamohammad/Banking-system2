package domain.model;

import domain.entite.Account;

public class CheckingAccount extends Account {

    private double monthlyFee;
    private boolean hasMonthlyFee;

    public CheckingAccount(String accountId, String ownerId, double balance) {
        this(accountId, ownerId, balance, 5.0); // رسوم افتراضية 5
    }

    public CheckingAccount(String accountId, String ownerId, double balance, double monthlyFee) {
        super(accountId, ownerId, balance);
        this.monthlyFee = monthlyFee;
        this.hasMonthlyFee = monthlyFee > 0;
    }

    public void applyMonthlyFee() {
        if (hasMonthlyFee && balance >= monthlyFee) {
            balance -= monthlyFee;
            System.out.printf("Applied monthly fee: $%.2f to account: %s%n",
                    monthlyFee, accountId);
        } else if (hasMonthlyFee) {
            System.out.println("Insufficient balance for monthly fee");
        }
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(double monthlyFee) {
        this.monthlyFee = monthlyFee;
        this.hasMonthlyFee = monthlyFee > 0;
    }

    public void waiveMonthlyFee() {
        this.hasMonthlyFee = false;
        System.out.println("Monthly fee waived for account: " + accountId);
    }

    @Override
    public String toString() {
        if (hasMonthlyFee) {
            return super.toString() + String.format(" [Checking, Monthly Fee: $%.2f]", monthlyFee);
        } else {
            return super.toString() + " [Checking, No Monthly Fee]";
        }
    }

}
