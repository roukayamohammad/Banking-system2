package bankingsystem.factory;

import domain.entities.Account;
import  domain.factory.AccountFactory;
import  domain.model.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * اختبارات لنمط Factory Pattern
 */
public class AccountFactoryTest {

    @Test
    @DisplayName("إنشاء حساب توفير باستخدام Factory")
    public void testCreateSavingsAccount() {
        // Given & When
        Account account = AccountFactory.createAccount(
                "SAVINGS", "SAV001", "CUST001", 1000.0
        );

        // Then
        assertNotNull(account, "يجب أن ينشئ حساباً");
        assertInstanceOf(SavingsAccount.class, account, "يجب أن يكون حساب توفير");
        assertEquals("SAV001", account.getAccountId());
        assertEquals(1000.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("إنشاء حساب جاري باستخدام Factory")
    public void testCreateCheckingAccount() {
        Account account = AccountFactory.createAccount(
                "CHECKING", "CHK001", "CUST001", 2000.0
        );

        assertInstanceOf(CheckingAccount.class, account);
        assertEquals(2000.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("إنشاء حساب قرض باستخدام Factory")
    public void testCreateLoanAccount() {
        Account account = AccountFactory.createAccount(
                "LOAN", "LN001", "CUST001", 5000.0
        );

        assertInstanceOf(LoanAccount.class, account);
        assertTrue(account.getBalance() < 0, "رصيد القرض يجب أن يكون سالباً");
    }

    @Test
    @DisplayName("إنشاء نوع حساب غير معروف يسبب خطأ")
    public void testUnknownAccountTypeThrowsException() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> AccountFactory.createAccount("UNKNOWN", "ACC001", "CUST001", 1000)
        );

        assertTrue(exception.getMessage().contains("Unknown account type"));
    }

    @Test
    @DisplayName("اختبار تسجيل نوع حساب جديد في Registry")
    public void testFactoryRegistry() {
        // يمكن اختبار إضافة أنواع جديدة
        // (هذا يعتمد على كيفية تنفيذ Registry في Factory)
    }
}