package org.example;

import domain.composite.AccountGroup;
import domain.composite.SingleAccount;
import domain.decorator.InsuranceDecorator;
import domain.decorator.OverdraftProtectionDecorator;
import domain.entities.Account;
import domain.model.CheckingAccount;
import domain.model.SavingsAccount;
import domain.state.ActiveState;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        System.out.println("=== اختبار سريع للـ Decorator Pattern ===");

        try {

        /* =====================================================
           1️⃣ اختبار OverdraftProtectionDecorator
           ===================================================== */
            System.out.println("\n1️⃣ اختبار OverdraftProtection:");

            Account acc1 = new SavingsAccount("TEST1", "User1", 1000);
            Account protectedAcc = new OverdraftProtectionDecorator(acc1);

            System.out.println(acc1);
            System.out.println("الرصيد قبل السحب: " + protectedAcc.getBalance());

            // سحب أكبر من الرصيد الأساسي (يستخدم السحب على المكشوف)
            protectedAcc.withdraw(1200);

            System.out.println("الرصيد بعد سحب 1200: " + protectedAcc.getBalance());


        /* =====================================================
           2️⃣ اختبار InsuranceDecorator
           ===================================================== */
            System.out.println("\n2️⃣ اختبار Insurance:");

            Account acc2 = new CheckingAccount("TEST2", "User2", 5000);
            Account insuredAcc = new InsuranceDecorator(acc2, 10000, 10);

            if (insuredAcc instanceof InsuranceDecorator) {

                InsuranceDecorator insurance = (InsuranceDecorator) insuredAcc;

                // الرصيد الفعلي = الرصيد الحقيقي + قيمة التغطية التأمينية
                System.out.println("الرصيد الفعلي مع التأمين: "
                        + insurance.getEffectiveBalance());

                // تقديم مطالبة تأمين
                insurance.fileClaim(2000);

                System.out.println("الرصيد بعد المطالبة: "
                        + insuredAcc.getBalance());
            }


        /* =====================================================
           3️⃣ سيناريو عملي متكامل للتأمين
           ===================================================== */
            System.out.println("\n3️⃣ سيناريو عملي للتأمين:");

            Account account = new CheckingAccount("CHK001", "Mohamed", 5000);
            Account insured = new InsuranceDecorator(account, 10000, 10);

            // 1. الرصيد الفعلي مع التغطية
            double effectiveBalance =
                    ((InsuranceDecorator) insured).getEffectiveBalance();
            System.out.println("الرصيد الفعلي مع التأمين: " + effectiveBalance);
            // 5000 + 10000 = 15000

            // 2. تقديم مطالبة (تعويض)
            ((InsuranceDecorator) insured).fileClaim(3000);
            System.out.println("الرصيد بعد التعويض: " + insured.getBalance());

            // 3. خصم القسط الشهري
            ((InsuranceDecorator) insured).applyMonthlyPremium();
            System.out.println("الرصيد بعد خصم القسط الشهري: " + insured.getBalance());


        /* =====================================================
           4️⃣ سيناريو عملي للسحب على المكشوف
           ===================================================== */
            System.out.println("\n4️⃣ سيناريو عملي للسحب على المكشوف:");

            Account account22 = new SavingsAccount("SAV001", "Ahmed", 1000);
            Account withOverdraft =
                    new OverdraftProtectionDecorator(account22, 500);

            // بدون الحماية: هذا السحب سيرفض
            System.out.println("محاولة سحب بدون حماية:");
            account22.withdraw(1200); // ❌ مرفوض

            // مع الحماية: السحب ينجح باستخدام السحب على المكشوف
            System.out.println("محاولة سحب مع الحماية:");
            withOverdraft.withdraw(1200); // ✅

            System.out.println(account22);

            System.out.println("الحد المتبقي للسحب على المكشوف: "
                    + ((OverdraftProtectionDecorator) withOverdraft)
                    .getAvailableOverdraft());

            // سحب إضافي
            System.out.println("سحب 200 إضافية:");
            withOverdraft.withdraw(200);

            System.out.println(((OverdraftProtectionDecorator) withOverdraft)
                    .getFeatures());

            // سداد جزء من الدين
            System.out.println("سداد 200 من السحب على المكشوف:");
            ((OverdraftProtectionDecorator) withOverdraft).repayOverdraft(200);

            System.out.println(((OverdraftProtectionDecorator) withOverdraft)
                    .getFeatures());

            System.out.println("\n✅ جميع الاختبارات تمت بنجاح!");

        } catch (RuntimeException exception) {
            System.out.println("❌ حدث خطأ: " + exception.getMessage());
        }
//    Account var1 = new Account("A1", "U1", 2200.0, new ActiveState()) {
//        };
//        System.out.println(var1.getBalance());
//        var1.deposit(99.0);
//        var1.getStateName();
//        System.out.println(var1.getStateName());
//        System.out.println(var1.getBalance());
//        Account var2 = new Account("A2", "U1", 200.0, new ActiveState()) {
//        };
//        Account var3 = new Account("A3", "U1", 200.0, new ActiveState()) {
//        };
//        SingleAccount var4 = new SingleAccount(var2);
//        SingleAccount var5 = new SingleAccount(var3);
//        AccountGroup var6 = new AccountGroup("This node one ");
//        var6.add(var4);
//        var6.add(var5);
//        var6.withdraw(200.0);
//        System.out.println(var6.getBalance());
    }
}