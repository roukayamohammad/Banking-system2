package domain.model;

import domain.entities.Account;
import domain.strategy.InvestmentInterestStrategy;

public class InvestmentAccount extends Account {


    public InvestmentAccount(String accountId, String ownerId, double balance) {
        super(accountId, ownerId, balance);
        this.interestStrategy = new InvestmentInterestStrategy(0.04);
    }

    @Override
    public String toString() {
        return super.toString() + " [Investment Account]";
    }
}
