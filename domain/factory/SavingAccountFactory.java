package domain.factory;

import domain.entite.Account;
import domain.state.AccountState;

public class SavingAccountFactory implements AccountFactory{

    @Override
    public Account creatAccount(String id, String owner, double balance,AccountState instate) {
       
    return new SavingAccount(id , owner, balance, instate);
    }

}
