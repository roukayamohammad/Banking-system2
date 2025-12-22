package domain.entities;

import domain.observer.Observer;
import domain.report.AuditService;
import domain.report.Transaction;
import domain.report.TransactionLog;
import domain.state.AccountState;
import domain.state.ActiveState;
import domain.strategy.InterestStrategy;

import java.util.ArrayList;
import java.util.List;
import domain.chain.*;

public abstract class Account {
    protected String accountId;
    protected String ownerId;
    protected double balance;
    protected AccountState state;
    protected InterestStrategy interestStrategy;


    private List<Observer> observers = new ArrayList<>();

    public Account(String accountId, String ownerId, double balance) {
        this.accountId = accountId;
        this.ownerId = ownerId;
        this.balance = balance;
        this.state = new ActiveState(this);
    }


    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }


    private void notifyObservers(String message) {
        for (Observer observer : observers) {
            //    observer.update("Account " + accountId + ": " + message);

            observer.update(this, message);
        }
    }


    public AccountState getState() {
        return state;
    }

    public void setState(AccountState state) {
        this.state = state;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;

        notifyObservers("Balance manually set to $" + balance);
    }


    public void increaseBalance(double amount) {
        this.balance += amount;
        TransactionLog.log(
                new Transaction(accountId, "DEPOSIT", amount)
        );

        notifyObservers("Deposited $" + amount + " | New Balance: $" + getBalance());
    }

    public void decreaseBalance(double amount) {
        this.balance -= amount;
        TransactionLog.log(
                new Transaction(accountId, "WITHDRAW", amount)
        );

        notifyObservers("Withdrawn $" + amount + " | New Balance: $" + getBalance());
    }


    public void deposit(double amount) {
        state.deposit(amount);
    }

    /*public void withdraw(double amount) {
        state.withdraw(amount);
    }*/


    public void withdraw(double amount) {


        if (amount > balance) {
            String msg = " Failed Attempt: Insufficient funds for withdrawal of $" + amount;
            System.out.println(msg);
            notifyObservers(msg);
            return;
        }


        TransactionHandler autoSystem = new AutoApprovalHandler();
        TransactionHandler manager = new ManagerHandler();
        TransactionHandler director = new DirectorHandler();

        autoSystem.setNextHandler(manager);
        manager.setNextHandler(director);


        TransactionRequest request = new TransactionRequest(this.accountId, amount, "Withdraw");

        System.out.println("\n--- Processing Withdrawal Approval ---");

        boolean isApproved = autoSystem.approve(request);


        if (isApproved) {
            System.out.println(" Chain Approved. Proceeding to Account State...");


            state.withdraw(amount);

        } else {

            String rejectionMsg = " Withdrawal Request of $" + amount + " was REJECTED by Bank Administration.";

            System.out.println(rejectionMsg);

            notifyObservers(rejectionMsg);
        }
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


    public void setInterestStrategy(InterestStrategy interestStrategy) {
        this.interestStrategy = interestStrategy;
    }

    public void applyInterest() {
        if (this.interestStrategy == null) {
            throw new IllegalStateException("Interest strategy not set");
        } else {
            double interest = this.interestStrategy.calculateInterest(this.balance);
            this.balance += interest;
            if (interest >= 0.0) {
                System.out.printf("Interest gain: $%.2f%n", interest);
            } else {
                System.out.printf("Interest loss: $%.2f%n", -interest);
            }

        }
    }

    public void changeState(AccountState newState) {
        String oldStateName = this.state.getName();
        this.state = newState;

        AuditService.log(
                "Account " + accountId + " state changed from " + oldStateName + " to " + newState.getName()
        );

        System.out.println("State Log: Changing account " + accountId + " from " + oldStateName + " to " + newState.getName());


        notifyObservers("State changed from " + oldStateName + " to " + newState.getName());
    }

    @Override
    public String toString() {
        return String.format("Account{id='%s', owner='%s', balance=%.2f, state=%s}",
                accountId, ownerId, balance, getStateName());
    }
}




/*package domain.entities;
import domain.state.AccountState;
import domain.state.ActiveState;
;

public abstract class Account {
    protected String accountId;
    protected String ownerId;
    protected double balance;
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