package domain.entite;


import domain.state.AccountState;
import domain.state.ActiveState;
import domain.state.SuspendedState;;

abstract public class Account {

     String accountId;
     String ownerId;
    public double balance;

    protected AccountState _state;

    public Account(String accountId, String ownerId, double balance, AccountState initialState) {
        this.accountId = accountId;
        this.ownerId = ownerId;
        this.balance = balance;

       this._state=initialState;
       this._state.setContext(this);
    }

    public void changeState(AccountState newState) {
        this._state = newState;
        this._state.setContext(this);
      
    }

    public void deposit(double amount) {
        _state.deposit(amount);
    }

    public void withdraw(double amount) {
        _state.withdraw(amount);
    }

    public String getStateName() {
        return _state.getName();
    }

    public String getAccountId() { return accountId; }
    public String getOwnerId() { return ownerId; }
    public double getBalance() { return balance; }
}