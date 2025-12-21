package bankingsystem.strategy;

import domain.strategy.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * اختبارات لنمط Strategy Pattern
 */
public class InterestStrategyTest {

    @Test
    @DisplayName("حساب فائدة حساب التوفير")
    public void testSavingsInterestStrategy() {
        // Given
        InterestStrategy strategy = new SavingsInterestStrategy(0.02);

        // When
        double interest = strategy.calculateInterest(10000.0);

        // Then
        assertEquals(200.0, interest, 0.001, "الفائدة يجب أن تكون 2% من 10000");
    }

    @Test
    @DisplayName("حساب فائدة القرض (على الرصيد السالب)")
    public void testLoanInterestStrategy() {
        // Given
        InterestStrategy strategy = new LoanInterestStrategy(0.05);

        // When
        double interest = strategy.calculateInterest(-20000.0); // قرض 20000

        // Then
        double expectedInterest = 20000.0 * (0.05 / 12); // فائدة شهرية
        assertEquals(expectedInterest, interest, 0.001);
    }

    @Test
    @DisplayName("فائدة القرض تعيد صفر للرصيد الموجب")
    public void testLoanInterestForPositiveBalance() {
        InterestStrategy strategy = new LoanInterestStrategy(0.05);
        double interest = strategy.calculateInterest(1000.0);

        assertEquals(0.0, interest, 0.001, "لا فائدة للرصيد الموجب");
    }

    @Test
    @DisplayName("عوائد حساب الاستثمار")
    public void testInvestmentInterestStrategy() {
        // Given
        InterestStrategy strategy = new InvestmentInterestStrategy(0.04);

        // When
        double returnAmount = strategy.calculateInterest(50000.0);

        // Then
        assertEquals(2000.0, returnAmount, 0.001, "العائد يجب أن يكون 4% من 50000");
    }

//    @Test
//    @DisplayName("تغيير الاستراتيجية في وقت التشغيل")
//    public void testStrategyChangeAtRuntime() {
//        // إنشاء حساب
//        Account account = AccountFactory.createAccount("SAVINGS", "TEST001", "TEST", 10000);
//
//        // استخدام استراتيجية بسيطة
//        account.setInterestStrategy(new SavingsInterestStrategy(0.02));
//        double simpleInterest = account.calculateInterest(30);
//
//        // تغيير إلى استراتيجية مركبة
//        account.setInterestStrategy(new CompoundInterestStrategy(0.02));
//        double compoundInterest = account.calculateInterest(30);
//
//        assertNotEquals(simpleInterest, compoundInterest,
//                "الاستراتيجيات المختلفة يجب أن تعطي نتائج مختلفة");
//    }
}