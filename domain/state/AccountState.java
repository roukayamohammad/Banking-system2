package domain.state;

import domain.entite.Account;

public abstract class AccountState {
    protected Account account;

    public AccountState(Account account) {
        this.account = account;
    }

    public abstract String getName();
    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);

        // عمليات تغيير الحالة
    public abstract void activate();
    public abstract void freeze();
    public abstract void suspend();
    public abstract void close();
}