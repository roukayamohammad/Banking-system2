package bankingsystem.decorator;


import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import   domain.entities.Account;
import   domain.factory.AccountFactory;
import   domain.decorator.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * اختبارات لنمط Decorator Pattern
 */
public class DecoratorTest {

    @Test
    @DisplayName("إضافة حماية السحب على المكشوف")
    public void testOverdraftProtection() {
        // Given
        Account basicAccount = AccountFactory.createAccount(
                "CHECKING", "CHK001", "CUST001", 1000.0
        );

        // When
        Account protectedAccount = new OverdraftProtectionDecorator(basicAccount, 500.0);

        // Then
        assertInstanceOf(OverdraftProtectionDecorator.class, protectedAccount);
        assertNotNull(((OverdraftProtectionDecorator)protectedAccount).getFeatures());
    }

    @Test
    @DisplayName("السحب باستخدام السحب على المكشوف")
    public void testWithdrawWithOverdraft() {
        Account basicAccount = AccountFactory.createAccount(
                "CHECKING", "CHK001", "CUST001", 1000.0
        );

        Account protectedAccount = new OverdraftProtectionDecorator(basicAccount, 500.0);

        // محاولة سحب أكثر من الرصيد
        protectedAccount.withdraw(1200.0);

        assertEquals(-200.0, protectedAccount.getBalance(), 0.001,
                "يجب أن يصبح الرصيد -200 بعد استخدام السحب على المكشوف");
    }

    @Test
    @DisplayName("السحب يتجاوز حد السحب على المكشوف يسبب خطأ")
    public void testWithdrawExceedsOverdraftLimit() {
        Account basicAccount = AccountFactory.createAccount(
                "CHECKING", "CHK001", "CUST001", 1000.0
        );

        Account protectedAccount = new OverdraftProtectionDecorator(basicAccount, 500.0);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> protectedAccount.withdraw(2000.0)
        );

        assertTrue(exception.getMessage().contains("Exceeds overdraft limit"));
    }

    @Test
    @DisplayName("إضافة تأمين على الحساب")
    public void testInsuranceDecorator() {
        Account basicAccount = AccountFactory.createAccount(
                "SAVINGS", "SAV001", "CUST001", 5000.0
        );

        Account insuredAccount = new InsuranceDecorator(basicAccount, 10000.0, 10.0);

        assertInstanceOf(InsuranceDecorator.class, insuredAccount);

        InsuranceDecorator insurance = (InsuranceDecorator) insuredAccount;
        assertEquals(15000.0, insurance.getEffectiveBalance(), 0.001,
                "الرصيد الفعلي يجب أن يشمل التغطية التأمينية");
    }

    @Test
    @DisplayName("المطالبة بالتأمين")
    public void testInsuranceClaim() {
        Account basicAccount = AccountFactory.createAccount(
                "SAVINGS", "SAV001", "CUST001", 5000.0
        );

        InsuranceDecorator insuredAccount = new InsuranceDecorator(
                basicAccount, 10000.0, 10.0
        );

        double initialBalance = insuredAccount.getBalance();
        boolean claimResult = insuredAccount.fileClaim(3000.0);

        assertTrue(claimResult, "يجب أن تنجح المطالبة");
        assertEquals(initialBalance + 3000.0, insuredAccount.getBalance(), 0.001);
    }

    @Test
    @DisplayName("تطبيق قسط التأمين الشهري")
    public void testInsurancePremium() {
        Account basicAccount = AccountFactory.createAccount(
                "SAVINGS", "SAV001", "CUST001", 1000.0
        );

        InsuranceDecorator insuredAccount = new InsuranceDecorator(
                basicAccount, 10000.0, 50.0
        );

        double initialBalance = insuredAccount.getBalance();
        insuredAccount.applyMonthlyPremium();

        assertEquals(initialBalance - 50.0, insuredAccount.getBalance(), 0.001,
                "يجب أن يخصم القسط الشهري");
    }
}