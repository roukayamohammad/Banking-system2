package domain.state;


import domain.entities.Account;

public class FrozenState extends AccountState {

    public FrozenState(Account account) {
        super(account);
    }

    @Override
    public String getName() {
        return "FROZEN";
    }

    @Override
    public void deposit(double amount) {
        // يمكن الإيداع للحساب المجمد (لتصفية الديون مثلاً)
        if (amount > 0) {
            double newBalance = account.getBalance() + amount;
            account.setBalance(newBalance);
            System.out.printf("Deposited $%.2f to frozen account %s. New balance: $%.2f%n",
                    amount, account.getAccountId(), newBalance);

            // إذا أصبح الرصيد إيجابياً، يمكن تفعيل الحساب
            if (newBalance > 0) {
                System.out.println("Account now has positive balance. Auto-activating...");
                activate();
            }
        }
    }

    @Override
    public void withdraw(double amount) {
        System.out.printf("Cannot withdraw $%.2f from frozen account %s.%n",
                amount, account.getAccountId());
        System.out.println("Please contact bank to unfreeze your account.");
    }

    @Override
    public void activate() {
        account.changeState(new ActiveState(account));
    }

    @Override
    public void freeze() {
        System.out.println("Account is already frozen");
    }

    @Override
    public void suspend() {
        System.out.println("Cannot suspend frozen account. Unfreeze first.");
    }

    @Override
    public void close() {
        // يمكن إغلاق الحساب المجمد إذا كان رصيده صفر
        if (account.getBalance() == 0) {
            account.changeState(new ClosedState(account));
        } else {
            System.out.println("Cannot close frozen account with balance.");
        }
    }
}