package domain.state;

import domain.entities.Account;

public class ClosedState extends AccountState{

    public ClosedState(Account account) {
        super(account);
    }

    @Override
    public void deposit(double amount) {
        // TODO Auto-generated method stub
        System.out.println("Cannot deposit to closed account");
//        throw new RuntimeException("الحساب معلق لا يمكن الايداع ");

    }

    @Override
    public void withdraw(double amount) {
       // TODO Auto-generated method stub
        System.out.println("Cannot withdraw from closed account");
//        throw new RuntimeException("الحساب معلق لا يمكن السحب ");

    }
    @Override
    public void activate() {
        System.out.println("Cannot activate closed account");
    }

    @Override
    public void freeze() {
        System.out.println("Cannot freeze closed account");
    }

    @Override
    public void suspend() {
        System.out.println("Cannot suspend closed account");
    }

    @Override
    public void close() {
        System.out.println("Account is already closed");
    }

    @Override
    public String getName() {
        return "CLOSED";
    }
}
