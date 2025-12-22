package domain.decorator;

import domain.entities.Account;
import domain.report.AuditService;

public class OverdraftProtectionDecorator extends AccountDecorator {
    private double overdraftLimit;
    private double usedOverdraft; //تتبع الاستخدام

    public OverdraftProtectionDecorator(Account account, double overdraftLimit) {
        super(account);
        this.overdraftLimit = overdraftLimit;
        this.usedOverdraft = 0;

        AuditService.log("Add Overdraft on " + accountId);

    }

    // Constructor ثانوي مع قيمة افتراضية
    public OverdraftProtectionDecorator(Account account) {
        this(account, 500.0); // قيمة افتراضية
    }

    @Override
    public void withdraw(double amount) {
        double available = decoratedAccount.getBalance() +
                (overdraftLimit - usedOverdraft);

        if (!decoratedAccount.getStateName().equals("ACTIVE")) {
            System.out.printf("❌ Cannot withdraw From %s Account", decoratedAccount.getStateName());
            return; // لا تتم العملية
        }
        if (amount <= available && amount > 0) {
            if (amount <= decoratedAccount.getBalance()) {
                decoratedAccount.withdraw(amount);
            } else {
                // استخدام السحب على المكشوف
                double overdraftNeeded = amount - decoratedAccount.getBalance();

                // 1. سحب كل الرصيد العادي أولاً
                decoratedAccount.withdraw(decoratedAccount.getBalance());
                // 2. إضافة السحب على المكشوف
                usedOverdraft += overdraftNeeded;
                decoratedAccount.decreaseBalance(overdraftNeeded);
                System.out.printf("استخدم السحب على المكشوف: %.2f%n", overdraftNeeded);
                System.out.printf("إجمالي السحب المستخدم: %.2f من %.2f%n",
                        usedOverdraft, overdraftLimit);
            }
            //  التغيير هنا: تحديث الرصيد المحلي
            this.balance = decoratedAccount.getBalance();
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