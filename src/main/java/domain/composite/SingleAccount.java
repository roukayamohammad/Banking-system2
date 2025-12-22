package domain.composite;

import domain.entities.Account;


public class SingleAccount extends AccountComponent {
    Account account;

    public SingleAccount(Account account) {
        this.account = account;
        this.name = account.getAccountId();   // ← مهم جدًا
    }

    @Override
    public double getBalance() {
        return account.getBalance();
    }

    @Override
    public void deposit(double amount) {
        account.deposit(amount);
    }

    @Override
    public void withdraw(double amount) {
        account.withdraw(amount);
    }

    @Override
    public void display(int n) {
        System.out.println(" ".repeat(n)
                + "- Account: " + account.getAccountId()
                + " | Balance: $" + account.getBalance());
    }
}
