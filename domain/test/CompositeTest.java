package domain.test;

import domain.composite.AccountComponent;
import domain.composite.AccountGroup;
import domain.composite.SingelAccount;
import domain.entities.Account;
import domain.model.InvestmentAccount;
import domain.model.SavingsAccount;

public class CompositeTest {
    



    public static void main(String[] args) {
 System.out.println("\n=== اختبار Composite Pattern ===\n");

        // إنشاء حسابات فردية
        Account acc1 = new SavingsAccount("S001", "Ali", 1200);
        Account acc2 = new SavingsAccount("S002", "Omar", 800);
        Account acc3 = new InvestmentAccount("INV001", "Sara", 5000);

        // تحويل الحسابات إلى SingleAccount (Leaf)
        AccountComponent single1 = new SingelAccount(acc1);
        AccountComponent single2 = new SingelAccount(acc2);
        AccountComponent single3 = new SingelAccount(acc3);

        // إنشاء مجموعات
        AccountGroup groupA = new AccountGroup("Group A");
        AccountGroup groupB = new AccountGroup("Group B");
        AccountGroup mainGroup = new AccountGroup("Main Group");

        // إضافة حسابات إلى Group A
        groupA.add(single1);
        groupA.add(single2);

        // إضافة حساب إلى Group B
        groupB.add(single3);

        // إضافة المجموعات إلى المجموعة الرئيسية
        mainGroup.add(groupA);
        mainGroup.add(groupB);

        // عرض الهيكل
        System.out.println("عرض هيكل الحسابات:");
        mainGroup.display(0);

        // حساب الرصيد الكلي
        System.out.println("\nإجمالي رصيد Main Group: " + mainGroup.getBalance());

        // تجربة الإيداع على المجموعة كاملة
        System.out.println("\nإيداع 100 لكل حساب داخل Main Group...");
        mainGroup.deposit(100);

        System.out.println("إجمالي الرصيد بعد الإيداع: " + mainGroup.getBalance());

        // تجربة السحب
        System.out.println("\nسحب 50 من كل حساب داخل Main Group...");
        mainGroup.withdraw(50);

        System.out.println("إجمالي الرصيد بعد السحب: " + mainGroup.getBalance());

        System.out.println("\n=== نهاية الاختبار ===");
    

    }
}


