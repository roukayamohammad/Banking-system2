// test/StatePatternDemo.java
package domain.test;

import domain.entities.Account;
import domain.factory.AccountFactory;
import domain.model.*;

public class StatePatternDemo {
    public static void main(String[] args) {
        System.out.println("=== تجربة State Pattern ===\n");

        // 1. إنشاء حساب جديد (حالة نشطة افتراضياً)
        Account account = AccountFactory.createAccount("SAVINGS", "ACC001", "Ahmed", 1000);

        System.out.println("1. الحساب بعد الإنشاء:");
        System.out.println(account);

        // 2. عمليات على الحساب النشط
        System.out.println("\n2. عمليات على الحساب النشط:");
        account.deposit(500);
        account.withdraw(200);

        // 3. تجميد الحساب
        System.out.println("\n3. تجميد الحساب:");
        account.freeze();
        System.out.println("الحالة بعد التجميد: " + account.getStateName());

        // 4. محاولة سحب من الحساب المجمد
        System.out.println("\n4. محاولة السحب من الحساب المجمد:");
        account.withdraw(100);

        // 5. إيداع للحساب المجمد وتفعيله تلقائياً
        System.out.println("\n5. إيداع للحساب المجمد:");
        account.deposit(200);

        // 6. تعليق الحساب
        System.out.println("\n6. تعليق الحساب:");
        account.suspend();
        System.out.println("الحالة بعد التعليق: " + account.getStateName());

        // 7. محاولة عمليات على الحساب المعلق
        System.out.println("\n7. عمليات على الحساب المعلق:");
        account.deposit(100);
        account.withdraw(50);

        // 8. إغلاق الحساب (يتطلب رصيد صفر)
        System.out.println("\n8. محاولة إغلاق حساب معلق:");
        account.close();
        System.out.println(account);

        // نعود للحالة النشطة أولاً
        Account account2 = new CheckingAccount("ACC002", "Mohamed", 500);

        System.out.println("\n9. محاولة اغلاق حساب نشط:");
        account2.close();

        // 9. سحب كل الأموال ثم الإغلاق
        System.out.println("\n9. سحب كل الأموال ثم الإغلاق:");
        account2.withdraw(500);
        account2.close();
        System.out.println(account2.toString());
        System.out.println("الحالة النهائية: " + account2.getStateName());

        // 10. عرض جميع الحالات الممكنة
//        System.out.println("\n10. مصفوفة التحولات المسموحة:");
//        displayTransitionMatrix();
    }

//    private static void displayTransitionMatrix() {
//        String[] states = {"ACTIVE", "FROZEN", "SUSPENDED", "CLOSED", "OVERDRAWN"};
//
//        System.out.println("\nمن \\ إلى\t" + String.join("\t", states));
//        System.out.println("--------------------------------------------------------");
//
//        for (int i = 0; i < states.length; i++) {
//            System.out.print(states[i] + "\t\t");
//            for (int j = 0; j < states.length; j++) {
//                String result = StateManager.canTransition(states[i], states[j]) ? "✓" : "✗";
//                System.out.print(result + "\t");
//            }
//            System.out.println();
//        }
//    }
}