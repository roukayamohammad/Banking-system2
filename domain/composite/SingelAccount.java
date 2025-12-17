package domain.composite;

import domain.entities.Account;

public class SingelAccount extends AccountComponent  {
    Account account;
public SingelAccount(Account account){
this.account=account;
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
         }
    
}
