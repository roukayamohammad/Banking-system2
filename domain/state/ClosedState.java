package domain.state;

import domain.entite.Account;

public class ClosedState extends AccountState{

    @Override
    public void deposit(double amount) {
        // TODO Auto-generated method stub
        throw new RuntimeException("الحساب معلق لا يمكن الايداع ");

    }

    @Override
    public void withdraw(double amount) {
       // TODO Auto-generated method stub
        throw new RuntimeException("الحساب معلق لا يمكن السحب ");

    }

    @Override
    public String getName() {
       return "SuspendedState"; }
}
