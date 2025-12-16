package domain.factory;

import domain.entite.Account;
import domain.state.AccountState;

public class CheckAccount extends Account {

    public CheckAccount(String accountId, String ownerId, double balance, AccountState initialState) {
        super(accountId, ownerId, balance, initialState);
        //TODO Auto-generated constructor stub
    }

    @Override
    public String getType() {
        
    return "Check";
    }
    
}
