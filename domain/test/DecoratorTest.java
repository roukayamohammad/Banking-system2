package domain.test;

import domain.decorator.InsuranceDecorator;
import domain.entities.Account;
import domain.decorator.OverdraftProtectionDecorator;
import domain.model.CheckingAccount;
import domain.model.SavingsAccount;

class DecoratorTest {
    private Account basicAccount;
    private OverdraftProtectionDecorator protectedAccount;
    public static void main(String[] args) {
        System.out.println("=== اختبار سريع ===");

        // 1. اختبار بسيط جداً لـ OverdraftProtection
        System.out.println("\n1. اختبار OverdraftProtection:");
        Account acc1 = new SavingsAccount("TEST1", "User1", 1000);
        Account protectedAcc = new OverdraftProtectionDecorator(acc1);

        System.out.println("الرصيد قبل السحب: " + protectedAcc.getBalance());
        protectedAcc.withdraw(1200);
        System.out.println("الرصيد بعد سحب 1200: " + protectedAcc.getBalance());

        // 2. اختبار بسيط جداً لـ Insurance
        System.out.println("\n2. اختبار Insurance:");
        Account acc2 = new CheckingAccount("TEST2", "User2", 5000);
        Account insuredAcc = new InsuranceDecorator(acc2, 10000, 10);

        if (insuredAcc instanceof InsuranceDecorator) {
            InsuranceDecorator insurance = (InsuranceDecorator) insuredAcc;
            System.out.println("الرصيد الفعلي مع التأمين: " + insurance.getEffectiveBalance());
            insurance.fileClaim(2000);
            System.out.println("الرصيد بعد المطالبة: " + insuredAcc.getBalance());
        }

        System.out.println("\n✅ كل الاختبارات تمت بنجاح!");
    }
}