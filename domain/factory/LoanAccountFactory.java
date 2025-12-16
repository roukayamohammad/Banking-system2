package domain.factory;

import domain.entite.Account;
import domain.state.AccountState;

public class LoanAccountFactory implements AccountFactory {

    @Override
    public Account creatAccount(String id, String owner, double balance,AccountState iniState) {
      return new LoanAccount(id, owner, balance, null);
    
    }
    
}
