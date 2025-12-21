package domain.composite;

 abstract public class AccountComponent {
    public abstract double getBalance();
      public abstract void deposit(double amount);
      
      public abstract void withdraw(double amount);
      public abstract void display(int n);
      String name;
      public String getName(){
        return name;
      }
    
}
