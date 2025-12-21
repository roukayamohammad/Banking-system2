package bankingsystem.composite;

import  domain.entities.Account;
import  domain.factory.AccountFactory;
import  domain.composite.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * اختبارات لنمط Composite Pattern
 */
public class CompositeTest {

    @Test
    @DisplayName("إنشاء مجموعة حسابات")
    public void testAccountGroupCreation() {
        // Given
        AccountGroup familyGroup = new AccountGroup("العائلة");

        // Then
        assertNotNull(familyGroup);
        assertEquals("العائلة", familyGroup.getName());
        assertEquals(0.0, familyGroup.getBalance(), 0.001);
    }

    @Test
    @DisplayName("إضافة حسابات فردية للمجموعة")
    public void testAddAccountsToGroup() {
        // Given
        AccountGroup group = new AccountGroup("المجموعة");
        Account acc1 = AccountFactory.createAccount("SAVINGS", "ACC1", "CUST1", 1000);
        Account acc2 = AccountFactory.createAccount("CHECKING", "ACC2", "CUST1", 2000);

        // When
        group.add(new SingleAccount(acc1));
        group.add(new SingleAccount(acc2));

        // Then
        assertEquals(3000.0, group.getBalance(), 0.001,
                "الرصيد الكلي يجب أن يكون مجموع أرصدة الحسابات");
    }

    @Test
    @DisplayName("الإيداع في المجموعة يوزع على جميع الحسابات")
    public void testGroupDeposit() {
        AccountGroup group = new AccountGroup("المجموعة");
        Account acc1 = AccountFactory.createAccount("SAVINGS", "ACC1", "CUST1", 1000);
        Account acc2 = AccountFactory.createAccount("CHECKING", "ACC2", "CUST1", 2000);

        group.add(new SingleAccount(acc1));
        group.add(new SingleAccount(acc2));

        double initialBalance = group.getBalance();
        group.deposit(500.0);

        assertEquals(initialBalance + 1000.0, group.getBalance(), 0.001,
                "الإيداع 500 في المجموعة يوزع 500 على كل حساب");
    }

    @Test
    @DisplayName("السحب من المجموعة يوزع على جميع الحسابات")
    public void testGroupWithdraw() {
        AccountGroup group = new AccountGroup("المجموعة");
        Account acc1 = AccountFactory.createAccount("SAVINGS", "ACC1", "CUST1", 1000);
        Account acc2 = AccountFactory.createAccount("CHECKING", "ACC2", "CUST1", 2000);

        group.add(new SingleAccount(acc1));
        group.add(new SingleAccount(acc2));

        double initialBalance = group.getBalance();
        group.withdraw(300.0);

        assertEquals(initialBalance - 600.0, group.getBalance(), 0.001,
                "السحب 300 من المجموعة يخصم 300 من كل حساب");
    }

    @Test
    @DisplayName("إزالة حساب من المجموعة")
    public void testRemoveAccountFromGroup() {
        AccountGroup group = new AccountGroup("المجموعة");
        Account acc1 = AccountFactory.createAccount("SAVINGS", "ACC1", "CUST1", 1000);
        SingleAccount singleAcc = new SingleAccount(acc1);

        group.add(singleAcc);
        assertEquals(1000.0, group.getBalance(), 0.001);

        group.remove(singleAcc);
        assertEquals(0.0, group.getBalance(), 0.001,
                "الرصيد يجب أن يصبح صفراً بعد إزالة الحساب");
    }
}