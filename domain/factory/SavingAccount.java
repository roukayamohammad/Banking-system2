package domain.factory;

import domain.entite.Account;
import domain.state.AccountState;

public class SavingAccount  extends Account{

    public SavingAccount(String accountId, String ownerId, double balance, AccountState initialState) {
        super(accountId, ownerId, balance, initialState);
      
    }

    @Override
    public String getType() {
    return "Saving"; 
    
    }
    
}
