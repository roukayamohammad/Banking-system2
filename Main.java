import domain.composite.AccountComponent;
import domain.composite.AccountGroup;
import domain.composite.SingelAccount;
import domain.entite.Account;
import domain.factory.AccountFactory;
import domain.factory.SavingAccountFactory;
import domain.state.ActiveState;
import domain.state.SuspendedState;

public class Main {
    public static void main(String[] args) {
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
Account c1=new Account("A1", "U1", 2200, new ActiveState()) {

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }
    
};
System.out.println(c1.getBalance());
c1.deposit(99);
c1.getStateName();

System.out.println(c1.getStateName());
System.out.println(c1.getBalance());
/*
تطبيق لل composit هون تحت 

*/

Account Account2=new Account("A2", "U1", 200, new ActiveState()) {

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }
    
};


Account Account3=new Account("A3", "U1", 200, new ActiveState()) {

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }
    
};

AccountComponent accountComponent=new SingelAccount(Account2);
AccountComponent accountComponent2=new SingelAccount(Account3);

AccountGroup node1=new AccountGroup("This node one ");
node1.add(accountComponent)
;
node1.add(accountComponent2)
;

node1.deposit(200);
System.out.println(node1.getBalance());



/*

هون تطبيق على ال  factory 



*/



AccountFactory factory=new SavingAccountFactory();
Account acc=factory.creatAccount("A4", "U1", 33, new ActiveState())
;

System.out.println(acc.getStateName());
System.out.println(acc.getBalance());
System.out.println(acc.getType());





    }
}
