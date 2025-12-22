package domain.test;

import domain.entities.Account;
import domain.factory.AccountFactory;

public class StudentAccountTest {

    public static void main(String[] args) {

        System.out.println("\n===== اختبار StudentAccount (حساب طلابي) =====\n");

        // 1) إنشاء حساب طلابي عبر الـ Factory
        Account student = AccountFactory.createAccount(
                "STUDENT",      // نوع الحساب
                "ST1001",       // رقم الحساب
                "Roukaya",      // صاحب الحساب
                100             // الرصيد الابتدائي
        );

        System.out.println("الحساب قبل أي عمليات:");
        System.out.println(student);

        // 2) تجربة الإيداع الأول (يجب أن يعطي Bonus)
        System.out.println("\nإجراء إيداع 60$ (أول إيداع)...");
        student.deposit(60);

        System.out.println("الرصيد بعد الإيداع + البونص:");
        System.out.println(student.getBalance());

        // 3) تجربة السحب ضمن الحد المسموح
        System.out.println("\nسحب 200$ (مسموح)...");
        student.withdraw(200);
        System.out.println("الرصيد بعد السحب:");
        System.out.println(student.getBalance());

        // 4) تجربة السحب فوق الحد (يجب أن يرمي خطأ)
        System.out.println("\nمحاولة سحب 400$ (غير مسموح)...");
        try {
            student.withdraw(400);
        } catch (Exception e) {
            System.out.println("خطأ: " + e.getMessage());
        }

        // 5) تطبيق الفائدة باستخدام Strategy
        System.out.println("\nتطبيق الفائدة (1%)...");
        student.applyInterest();
        System.out.println("الرصيد بعد الفائدة:");
        System.out.println(student.getBalance());

        System.out.println("\n===== نهاية الاختبار =====");
    }
}
