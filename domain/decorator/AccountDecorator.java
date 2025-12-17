package domain.decorator;

import domain.entite.Account;

import java.math.BigDecimal;

abstract class AccountDecorator extends Account {

    protected Account decoratedAccount;

    public AccountDecorator(Account account) {

        super(account.getAccountId(), account.getOwnerId(), account.getBalance());
        this.decoratedAccount = account;
        // نسخ الحالة
        this.setState(account.getState());
    }


    @Override
    public void deposit(double amount) {
        decoratedAccount.deposit(amount);
        updateBalance();
    }

    @Override
    public void withdraw(double amount) {
        decoratedAccount.withdraw(amount);
        updateBalance();
    }

    @Override
    public double getBalance() {
        return decoratedAccount.getBalance();
    }

    protected void updateBalance() {
        // Update local balance reference
        this.balance = decoratedAccount.getBalance();
    }

    public abstract String getFeatures();

}
