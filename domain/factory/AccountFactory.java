package domain.factory;

import domain.entite.Account;
import domain.state.AccountState;

public interface AccountFactory {
    Account creatAccount(String id,String owner ,double balance,AccountState iniState);

}
