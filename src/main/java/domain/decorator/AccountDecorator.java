package domain.decorator;

import domain.entities.Account;
import domain.state.AccountState;

abstract class AccountDecorator extends Account {

    protected Account decoratedAccount;

    public AccountDecorator(Account account) {

        super(account.getAccountId(), account.getOwnerId(), 0);
        this.decoratedAccount = account;
        // نسخ الحالة
        this.setState(account.getState());
    }


    @Override
    public void deposit(double amount) {
        decoratedAccount.deposit(amount);
    }

    @Override
    public void withdraw(double amount) {
        decoratedAccount.withdraw(amount);
    }

    @Override
    public double getBalance() {
        return decoratedAccount.getBalance();
    }

    @Override
    public AccountState getState() {
        return decoratedAccount.getState();
    }

    public abstract String getFeatures();

    @Override
    public void freeze() {
        decoratedAccount.freeze();
    }

    @Override
    public void suspend() {
        decoratedAccount.suspend();
    }

    @Override
    public void close() {
        decoratedAccount.close();
    }

    @Override
    public String getStateName() {
        return decoratedAccount.getStateName();
    }

}
