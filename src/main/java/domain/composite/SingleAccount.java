package domain.composite;

import domain.entities.Account;

public class SingleAccount extends AccountComponent {
    Account account;

    public SingleAccount(Account account) {
        this.account = account;
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
        System.out.println(" ".repeat(n) + account);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SingleAccount)) return false;
        SingleAccount other = (SingleAccount) obj;
        return this.account.getAccountId().equals(other.account.getAccountId());
    }

    @Override
    public int hashCode() {
        return account.getAccountId().hashCode();
    }

}
