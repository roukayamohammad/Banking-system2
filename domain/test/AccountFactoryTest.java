/*
package domain.test;

import domain.entities.Account;
import domain.factory.AccountFactory;
import domain.model.SavingsAccount;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
*/
/*
هذا يختبر:
الإنشاء
النوع
القيم الأساسية
 *//*

public class AccountFactoryTest {

    @Test
    void shouldCreateSavingsAccount() {
        Account account = AccountFactory.createAccount(
                "SAVINGS", "A1", "C1", 1000
        );

        assertNotNull(account);
        assertTrue(account instanceof SavingsAccount);
        assertEquals(1000, account.getBalance());
        assertEquals("A1", account.getAccountId());
        assertEquals("C1", account.getOwnerId());
    }

    @Test
    void shouldCreateCheckingAccount() {
        Account account = AccountFactory.createAccount(
                "CHECKING", "A2", "C2", 500
        );

        assertNotNull(account);
        assertTrue(account instanceof CheckingAccount);
        assertEquals(500, account.getBalance());
    }

    @Test
    void shouldThrowExceptionForUnknownAccountType() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> AccountFactory.createAccount("UNKNOWN", "A3", "C3", 100)
        );

        assertTrue(exception.getMessage().contains("Unknown account type"));
    }

}
*/
