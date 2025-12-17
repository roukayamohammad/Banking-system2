package domain.decorator;

import domain.entities.Account;

public class OverdraftProtectionDecorator extends AccountDecorator {
    private double overdraftLimit;
    private double usedOverdraft; //تتبع الاستخدام

    public OverdraftProtectionDecorator(Account account, double overdraftLimit) {
        super(account);
        this.overdraftLimit = overdraftLimit;
        this.usedOverdraft = 0;
    }

    // Constructor ثانوي مع قيمة افتراضية
    public OverdraftProtectionDecorator(Account account) {
        this(account, 500.0); // قيمة افتراضية
    }

    @Override
    public void withdraw(double amount) {

        double available = decoratedAccount.getBalance()
                + (overdraftLimit - usedOverdraft);

        if (amount <= available) {
            if (amount <= decoratedAccount.getBalance()) {
                decoratedAccount.withdraw(amount);
            } else {
                double overdraftNeeded = amount - decoratedAccount.getBalance();
                decoratedAccount.withdraw(decoratedAccount.getBalance());
                usedOverdraft += overdraftNeeded;
                System.out.println("Using overdraft: $" + overdraftNeeded);
            }
        } else {
            throw new RuntimeException("Exceeds overdraft limit");
        }
    }
//سداد السحب
    public void repayOverdraft(double amount) {
        if (amount > 0 && amount <= usedOverdraft) {
            usedOverdraft -= amount;
            System.out.println("Overdraft repaid: $" + amount +
                    " | Remaining: $" + usedOverdraft);
        }
    }

    public double getAvailableOverdraft() {
        return overdraftLimit - usedOverdraft;
    }

    @Override
    public String getFeatures() {
        return "Overdraft Protection [Limit: $" + overdraftLimit +
                ", Used: $" + usedOverdraft + "]";
    }
}