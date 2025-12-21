package domain.model;

import domain.entities.Account;
import domain.strategy.SavingsInterestStrategy;

public class SavingsAccount extends Account {


    public SavingsAccount(String accountId, String ownerId, double balance) {
        super(accountId, ownerId, balance);
        this.interestStrategy = new SavingsInterestStrategy(0.02);
    }

    @Override
    public String toString() {
        return super.toString() + " [Savings Account]";
    }

}

