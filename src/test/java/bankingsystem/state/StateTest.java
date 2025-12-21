package bankingsystem.state;

import domain.entities.Account;
import domain.factory.AccountFactory;
import domain.state.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * اختبارات لنمط State Pattern
 */
public class StateTest {

    @Test
    @DisplayName("الحالة الابتدائية للحساب الجديد هي Active")
    public void testInitialAccountState() {
        Account account = AccountFactory.createAccount(
                "SAVINGS", "SAV001", "CUST001", 1000.0
        );

        assertEquals("ACTIVE", account.getStateName(),
                "الحالة الابتدائية يجب أن تكون Active");
    }

    @Test
    @DisplayName("تجميد الحساب")
    public void testFreezeAccount() {
        Account account = AccountFactory.createAccount(
                "SAVINGS", "SAV001", "CUST001", 1000.0
        );

        account.freeze();

        assertEquals("FROZEN", account.getStateName(),
                "الحالة يجب أن تصبح Frozen");
    }

    @Test
    @DisplayName("الإيداع في الحساب المجمد")
    public void testDepositToFrozenAccount() {
        Account account = AccountFactory.createAccount(
                "SAVINGS", "SAV001", "CUST001", 1000.0
        );

        account.freeze();
        double initialBalance = account.getBalance();

        // الحساب المجمد يمكنه الإيداع
        account.deposit(500.0);

        assertEquals(initialBalance + 500.0, account.getBalance(), 0.001,
                "يمكن الإيداع للحساب المجمد");
    }

    @Test
    @DisplayName("السحب من الحساب المجمد ممنوع")
    public void testWithdrawFromFrozenAccount() {
        Account account = AccountFactory.createAccount(
                "SAVINGS", "SAV001", "CUST001", 1000.0
        );

        account.freeze();
        double initialBalance = account.getBalance();

        // المحاولة لا تسبب خطأ ولكن تمنع العملية
        account.withdraw(500.0);

        assertEquals(initialBalance, account.getBalance(), 0.001,
                "لا يمكن السحب من الحساب المجمد");
    }

    @Test
    @DisplayName("تعليق الحساب")
    public void testSuspendAccount() {
        Account account = AccountFactory.createAccount(
                "SAVINGS", "SAV001", "CUST001", 1000.0
        );

        account.suspend();

        assertEquals("SUSPENDED", account.getStateName(),
                "الحالة يجب أن تصبح Suspended");
    }

    @Test
    @DisplayName("إغلاق الحساب")
    public void testCloseAccount() {
        Account account = AccountFactory.createAccount(
                "SAVINGS", "SAV001", "CUST001", 0.0 // رصيد صفر
        );

        account.close();

        assertEquals("CLOSED", account.getStateName(),
                "الحالة يجب أن تصبح Closed عندما الرصيد صفر");
    }

    @Test
    @DisplayName("لا يمكن إغلاق حساب له رصيد")
    public void testCannotCloseAccountWithBalance() {
        Account account = AccountFactory.createAccount(
                "SAVINGS", "SAV001", "CUST001", 1000.0
        );

        // الحساب له رصيد، لا يمكن إغلاقه
        account.close();

        assertNotEquals("CLOSED", account.getStateName(),
                "لا يمكن إغلاق حساب له رصيد");
    }
}