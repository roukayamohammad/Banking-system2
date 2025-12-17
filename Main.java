import domain.composite.AccountComponent;
import domain.composite.AccountGroup;
import domain.composite.SingelAccount;
import domain.entite.Account;
import domain.factory.AccountFactory;
import domain.state.ActiveState;
import domain.state.ClosedState;
import domain.state.SuspendedState;

public class Main {
    public static void main(String[] args) {

        Account axx = AccountFactory.createAccount("SAVINGS", "2", "all", 20);
        System.out.println( axx.toString());
        axx.deposit(100);
        System.out.println( axx.toString());
        axx.withdraw(10);
        System.out.println( axx.toString()+"\n");
        axx.close();
        axx.withdraw(110);
        System.out.println( axx.toString()+"\n");
        axx.close();
        System.out.println( axx.toString());


        //   Account s1 = new SavingsAccount("A1", "U1", 10000);
        //     Account c1 = new CheckingAccount("A2", "U1", 500);

        // s1.internalDeposit(0);
        // AccountGroup family = new AccountGroup("G1", "U1");
        // family.add(s1);
        // family.add(c1);

        // family.deposit(100); // يوزّع 100 لكل حساب
        // System.out.println(s1.getBalance()); // 1100
        // System.out.println(c1.getBalance()); // 600


        // s1.setState(new ActiveState());
        // try {

        //     s1.withdraw(50);
        //      // يرمي استثناء لأن الحساب مجمّد
        //      System.out.println("hhhhh");
        // } catch (Exception e) {
        //     System.out.println(e.getMessage()); // Account is frozen
        // }

//Account c1=new
//        Account c1 = new Account("A1", "U1", 2200, new ActiveState()) {
//
//        };
//        System.out.println(c1.getBalance());
//        c1.deposit(99);
//        c1.getStateName();
//
//        System.out.println(c1.getStateName());
//        System.out.println(c1.getBalance());
/*
تطبيق لل composit هون تحت 

*/

//        Account Account2 = new Account("A2", "U1", 200, new ActiveState()) {
//
//        };
//
//
//        Account Account3 = new Account("A3", "U1", 200, new ActiveState()) {
//
//        };
//
//        AccountComponent accountComponent = new SingelAccount(Account2);
//        AccountComponent accountComponent2 = new SingelAccount(Account3);
//
//        AccountGroup node1 = new AccountGroup("This node one ");
//        node1.add(accountComponent)
//        ;
//        node1.add(accountComponent2)
//        ;
//
//        node1.deposit(200);
//        System.out.println(node1.getBalance());


    }
}
