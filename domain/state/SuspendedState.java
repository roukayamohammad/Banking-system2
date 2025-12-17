package domain.state;

import domain.entities.Account;

public class SuspendedState extends AccountState {

    public SuspendedState(Account account) {
        super(account);
    }

    @Override
    public String getName() {
        return "SUSPENDED";
    }

    @Override
    public void deposit(double amount) {
//        throw new RuntimeException("Cannot deposit to suspended account");
            System.out.println("Cannot deposit to suspended account");
    }

    @Override
    public void withdraw(double amount) {
        // TODO Auto-generated method stub
//        throw new RuntimeException("الحساب معلق لا يمكن السحب ");
            System.out.println("Cannot withdraw from suspended account");
    }

    @Override
    public void activate() {
        System.out.println("Cannot activate suspended account directly.");
        System.out.println("Please contact customer service to resolve account suspension.");
    }

    @Override
    public void freeze() {
        System.out.println("Cannot freeze suspended account.");
    }

    @Override
    public void suspend() {
        System.out.println("Account is already suspended");
    }

    @Override
    public void close() {
        // يمكن إغلاق الحساب المعلق بعد التحقق
        System.out.println("Closing suspended account. Requires manager approval...");
        account.changeState(new ClosedState(account));
    }
}