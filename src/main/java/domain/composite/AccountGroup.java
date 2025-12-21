package domain.composite;

import java.util.ArrayList;
import java.util.List;

public class AccountGroup extends AccountComponent {
private List<AccountComponent>children=new ArrayList<>();
public AccountGroup(String name){
this.name=name;
}


  @Override
    public double getBalance() {
        return children.stream()
                .mapToDouble(AccountComponent::getBalance)
                .sum();
    }

    @Override
    public void deposit(double amount) {
        for (AccountComponent c : children) {
            c.deposit(amount);
        }
    }

    @Override
    public void withdraw(double amount) {
        for (AccountComponent c : children) {
            c.withdraw(amount);
        }
    }

    @Override
    public void display(int indent) {
        System.out.println(" ".repeat(indent) + "Group: " + name);
        for (AccountComponent c : children) {
            c.display(indent + 4);
        }
    }
    
/*
هلأ هدول التوابع الخاصين بالاضافة و الحذف يعني بمثابة يضيفو ابناء 


*/
public void  add(AccountComponent accountComponent){
    children.add(accountComponent);
}
public void  remove(AccountComponent accountComponent){
    children.remove(accountComponent);
}













}
