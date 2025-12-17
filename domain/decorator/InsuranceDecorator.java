package domain.decorator;

import domain.entite.Account;

public class InsuranceDecorator extends AccountDecorator {
    private double coverageAmount;
    private double monthlyPremium;
    private boolean isActive;

    public InsuranceDecorator(Account account, double coverageAmount, double monthlyPremium) {
        super(account);
        this.coverageAmount = coverageAmount;
        this.monthlyPremium = monthlyPremium;
        this.isActive = true;
    }

    @Override
    public void deposit(double amount) {
        decoratedAccount.deposit(amount);
        updateBalance();
    }

    public double getEffectiveBalance() {
        return decoratedAccount.getBalance() + (isActive ? coverageAmount : 0);
    }

    public void applyMonthlyPremium() {
        if (isActive) {
            decoratedAccount.withdraw(monthlyPremium);
            updateBalance();
            System.out.println("Insurance premium applied: $" + monthlyPremium);
        }
    }

    public boolean fileClaim(double amount) {
        if (isActive && amount <= coverageAmount) {
            decoratedAccount.deposit(amount);
            updateBalance();
            System.out.println("Insurance claim paid: $" + amount);
            return true;
        }
        return false;
    }

    @Override
    public String getFeatures() {
        return "Insurance Coverage [Amount: $" + coverageAmount +
                ", Premium: $" + monthlyPremium + "/month, Active: " + isActive + "]";
    }
}