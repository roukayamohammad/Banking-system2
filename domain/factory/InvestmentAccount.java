package domain.factory;

import domain.entite.Account;
import domain.state.AccountState;
//concrete product 
/*

ال  Account يعتبر  abstract product

*/
public class InvestmentAccount extends Account{

    public InvestmentAccount(String accountId, String ownerId, double balance, AccountState initialState) {
        super(accountId, ownerId, balance, initialState);
        //TODO Auto-generated constructor stub
    }

    @Override
    public String getType() {
       return "InvestmentAccount"; }
    
}
