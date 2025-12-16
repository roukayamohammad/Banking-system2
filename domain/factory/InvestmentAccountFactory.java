package domain.factory;

import domain.entite.Account;
import domain.state.AccountState;

public class InvestmentAccountFactory implements AccountFactory{

    @Override
    public Account creatAccount(String id, String owner, double balance, AccountState iniState) {
    return new InvestmentAccount(owner, owner, balance, iniState);
      
    
    }
    
}
