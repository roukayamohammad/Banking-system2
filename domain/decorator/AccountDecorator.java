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

}
