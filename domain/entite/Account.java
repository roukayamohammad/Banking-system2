package domain.entite;


import domain.state.AccountState;
import domain.state.ActiveState;
import domain.state.SuspendedState;;

public abstract class Account {
    protected String accountId;
    protected String ownerId;
    public double balance;
    protected AccountState state;

    public AccountState getState() {
        return state;
    }

    public Account(String accountId, String ownerId, double balance) {
        this.accountId = accountId;
        this.ownerId = ownerId;
        this.balance = balance;
        // Initialize with default state
        this.state = new ActiveState(this); // Set initial state
    }

    // Setter for state - used by State Pattern
    public void setState(AccountState state) {
        this.state = state;
    }

    // State operations
    public void deposit(double amount) {
        state.deposit(amount);
    }

    public void withdraw(double amount) {
        state.withdraw(amount);
    }

    public void freeze() {
        state.freeze();
    }

    public void suspend() {
        state.suspend();
    }

    public void close() {
        state.close();
    }

    public void activate() {
        state.activate();
    }
    public String getStateName() {
        return state.getName();
    }

    // Getters
    public String getAccountId() {
        return accountId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public double getBalance() {
        return balance;
    }

    // Setter for balance (should be used carefully)
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return String.format("Account{id='%s', owner='%s', balance=%.2f, state=%s}",
                accountId, ownerId, balance, getStateName());
    }
    // مهم: هذه الدالة تسمح للحالات بتغيير حالة الحساب
    public void changeState(AccountState newState) {
        System.out.println("Changing account " + accountId + " state from " +
                this.state.getName() + " to " + newState.getName());
        this.state = newState;
    }
}


/*bstract public class Account {

     String accountId;
     String ownerId;
    public double balance;

    protected AccountState _state;

    public Account(String accountId, String ownerId, double balance) {
        this.accountId = accountId;
        this.ownerId = ownerId;
        this.balance = balance;
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
}*/