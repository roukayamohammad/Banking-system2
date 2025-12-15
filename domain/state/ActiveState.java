package domain.state;

import domain.entite.Account;

public class ActiveState extends AccountState{


  


    @Override
    public void deposit(double amount) {
        account.balance+=amount;
        }

    @Override
    public void withdraw(double amount) {
        if (account.balance>=amount) {
            account.balance-=amount;
        }
        else{
            throw new RuntimeException("كمية السحب كبيرة ");
        }
    }

    @Override
    public String getName() {
      return "Active";
    }

  


}