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

        try{
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


            Account account = new CheckingAccount("CHK001", "Mohamed", 5000);
            Account insured = new InsuranceDecorator(account, 10000, 10);

// 1. تغطية إضافية
            double effectiveBalance = ((InsuranceDecorator)insured).getEffectiveBalance();
// الرصيد الفعلي = 5000 + 10000 (تأمين) = 15000

// 2. في حالة سرقة البطاقة
            ((InsuranceDecorator)insured).fileClaim(3000);
// البنك يدفع 3000 كتعويض
// الرصيد الجديد: 8000 (5000 + 3000 تعويض)

// 3. الدفع الشهري للتأمين
            ((InsuranceDecorator)insured).applyMonthlyPremium();
// يخصم 10 شهرياً من الرصيد


            System.out.println();
            // مثال عملي:
            Account account22 = new SavingsAccount("SAV001", "Ahmed", 1000);
            Account withOverdraft = new OverdraftProtectionDecorator(account22, 500);

// بدون الحماية: هذا السحب سيفشل
            account22.withdraw(1200); // ❌ يرفض (الرصيد: 1000 فقط)

// مع الحماية: هذا السحب ينجح
            withOverdraft.withdraw(1200); // ✅ ينجح
            System.out.println(account22);
// النتيجة:
// - يسحب 1000 من الرصيد الأساسي
// - يسحب 200 من حد السحب على المكشوف
// - الرصيد يصبح: -200 (مدين)
// - الحد المتبقي للسحب: 300

//            withOverdraft.withdraw(400);
//        System.out.println(account22);
            System.out.println(((OverdraftProtectionDecorator)withOverdraft).getAvailableOverdraft()+ "الحد المتبقي للسحب:");

            System.out.println("سحب 200");
            withOverdraft.withdraw(200); // ✅ ينجح

            System.out.println(((OverdraftProtectionDecorator)withOverdraft).getFeatures()+ "كميه الدين والمتبقي");

// يمكن سداد الدين لاحقاً
            System.out.println("نسدسد 200");
        ((OverdraftProtectionDecorator)withOverdraft).repayOverdraft(200);
        System.out.println(((OverdraftProtectionDecorator)withOverdraft).getFeatures()+ "كميه الدين والمتبقي");

        }catch (RuntimeException exception){
            System.out.println(exception);
        }

    }
}