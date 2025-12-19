package domain.entities;


import domain.state.AccountState;
import domain.state.ActiveState;
import domain.strategy.InterestStrategy;

public abstract class Account {
    protected String accountId;
    protected String ownerId;
    protected double balance;
    protected AccountState state;
    protected InterestStrategy interestStrategy;

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

    public void increaseBalance(double amount) {
        this.balance += amount;
    }

    public void decreaseBalance(double amount) {
        this.balance -= amount;
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


    /**
     * تعيين استراتيجية الفائدة (Runtime)
     */
    public void setInterestStrategy(InterestStrategy interestStrategy) {
        this.interestStrategy = interestStrategy;
    }

    /**
     * تطبيق الفائدة باستخدام Strategy
     */

    public void applyInterest() {
        if (interestStrategy == null)
            throw new IllegalStateException("Interest strategy not set");

        double interest = interestStrategy.calculateInterest(balance);
        balance += interest;

        if (interest >= 0) {
            System.out.printf("Interest gain: $%.2f%n", interest);
        } else {
            System.out.printf("Interest loss: $%.2f%n", -interest);
        }
    }


}
