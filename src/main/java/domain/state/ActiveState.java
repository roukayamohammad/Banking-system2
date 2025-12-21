package domain.state;

import domain.entities.Account;

public class ActiveState extends AccountState {

    public ActiveState(Account account) {
        super(account);
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            account.increaseBalance(amount);
            System.out.println("Depositing " + amount + " to active account");
        }

    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("مبلغ السحب يجب أن يكون موجباً");
            return;
        }

        double currentBalance = account.getBalance();
        if (amount <= currentBalance) {
            // سحب عادي
            account.decreaseBalance(amount);
            System.out.printf("سحب %.2f من حساب نشط. الرصيد الجديد: %.2f%n",
                    amount, account.getBalance());
        } else {
            System.out.printf("الرصيد الحالي %.2f غير كافٍ للسحب %.2f%n",
                    currentBalance, amount);
            System.out.println("التحقق من حماية السحب على المكشوف...");
        }
    }

    @Override
    public String getName() {
        return "ACTIVE";
    }

    @Override
    public void activate() {
        System.out.println("Account is already active");
    }

    @Override
    public void freeze() {
        account.changeState(new FrozenState(account));
    }

    @Override
    public void suspend() {
        account.changeState(new SuspendedState(account));
    }

    @Override
    public void close() {
        // تحقق من وجود رصيد صفري
        if (account.getBalance() == 0) {
            account.changeState(new ClosedState(account));
        } else {
            System.out.println("Cannot close account with balance. Please withdraw all funds first.");
        }
    }


    @Override
    public String toString() {
        return "ActiveState";
    }

}