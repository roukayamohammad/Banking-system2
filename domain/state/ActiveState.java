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
        if (account.getBalance() >= amount) {
            account.decreaseBalance(amount);
            System.out.println("Withdraw " + amount + " from active account");
        } else {
            throw new RuntimeException("كمية السحب كبيرة ");
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