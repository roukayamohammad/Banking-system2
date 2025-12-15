package domain.state;

import domain.entite.Account;

public abstract class AccountState{
   public Account account;
 
 public void setContext(Account account){
this.account=account;
 }
      public abstract void deposit(double amount);
    public abstract void withdraw(double amount);
    public abstract String getName();
     
}