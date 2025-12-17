package domain.decorator;

import domain.entite.Account;

public class OverdraftProtectionDecorator extends AccountDecorator {
    private double overdraftLimit;
    private double usedOverdraft;

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
        double availableBalance = decoratedAccount.getBalance() +
                (overdraftLimit - usedOverdraft);

        if (amount <= availableBalance) {
            if (amount <= decoratedAccount.getBalance()) {
                // Use regular balance
                decoratedAccount.withdraw(amount);
            } else {
                // Use overdraft
                double overdraftNeeded = amount - decoratedAccount.getBalance();
                decoratedAccount.withdraw(decoratedAccount.getBalance()); // Empty regular balance
                usedOverdraft += overdraftNeeded;
                System.out.println("Using overdraft: $" + overdraftNeeded +
                        " | Total overdraft used: $" + usedOverdraft);
            }
            updateBalance();
        } else {
            System.out.println("Withdrawal denied: Exceeds available balance and overdraft limit");
        }
    }

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