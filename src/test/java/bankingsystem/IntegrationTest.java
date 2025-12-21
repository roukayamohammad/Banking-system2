package bankingsystem;

import domain.entities.Account;
import domain.entities.Customer;
import domain.factory.AccountFactory;
import domain.decorator.*;
import domain.composite.*;
import domain.strategy.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * اختبارات تكاملية بين أنماط التصميم المختلفة
 */
public class IntegrationTest {

    @Test
    @DisplayName("تطبيق أنماط متعددة معاً")
    public void testMultiplePatternsTogether() {
        // 1. Factory: إنشاء حساب
        Account basicAccount = AccountFactory.createAccount(
                "SAVINGS", "INT001", "CUST001", 5000.0
        );

        // 2. Decorator: إضافة ميزات
        Account withOverdraft = new OverdraftProtectionDecorator(basicAccount, 1000.0);
        Account withInsurance = new InsuranceDecorator(withOverdraft, 10000.0, 10.0);

//        // 3. Strategy: تعيين استراتيجية فائدة
//        withInsurance.setInterestStrategy(new CompoundInterestStrategy(0.02));

        // 4. State: تغيير الحالة
        withInsurance.freeze();

        assertEquals("FROZEN", withInsurance.getStateName());

        // 5. Composite: إضافة للمجموعة
        AccountGroup group = new AccountGroup("التكامل");
        group.add(new SingleAccount(withInsurance));

        // التحقق من عمل كل شيء معاً
        assertNotNull(withInsurance);
        assertTrue(withInsurance.getBalance() > 0);
        assertNotNull(group);
        assertEquals(5000.0, group.getBalance(), 0.001);
    }

    @Test
    @DisplayName("عميل مع حسابات متعددة وأنماط مختلفة")
    public void testCustomerWithMultipleAccountsAndPatterns() {
        // إنشاء عميل
        Customer customer = new Customer("CUST001", "أحمد",
                "ahmed@email.com", "0501234567", "الرياض");

        // إنشاء حسابات بأنماط مختلفة
        Account savings = AccountFactory.createAccount("SAVINGS", "SAV001", "CUST001", 10000);
        savings.setInterestStrategy(new SavingsInterestStrategy(0.02));

        Account checking = AccountFactory.createAccount("CHECKING", "CHK001", "CUST001", 5000);

        System.out.println("محاولة سحب 4400 بدون استخدم السحب على المكشوف");
        checking.withdraw(5400); //  بدون استخدم السحب على المكشوف

        checking = new OverdraftProtectionDecorator(checking, 1000);

        // إضافة الحسابات للعميل
        customer.addAccount(savings);
        customer.addAccount(checking);

        // التحقق
        assertEquals(2, customer.getAccounts().size());
        assertEquals(15000.0, customer.getTotalBalance(), 0.001);

        // اختبار عمليات
        checking.withdraw(5400); // يستخدم السحب على المكشوف
        System.out.println("الرصيد الحالي:" + String.valueOf(checking.getBalance()));
        assertTrue(checking.getBalance() < 0);

        // تطبيق الفائدة
        savings.applyInterest();
        assertTrue(savings.getBalance() > 10000);
    }
}