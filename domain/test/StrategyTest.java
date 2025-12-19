package domain.test;

import domain.entities.Account;
import domain.model.InvestmentAccount;
import domain.model.SavingsAccount;
import domain.strategy.SavingsInterestStrategy;

public class StrategyTest {


    public static void main(String[] args) {

        System.out.println("\n=== اختبار Strategy Pattern ===");

// حساب توفير
        Account savings = new SavingsAccount("S001", "Ali", 1000);
        System.out.println("حساب توفير" + savings);

// تطبيق الفائدة
        savings.applyInterest();
        System.out.println("الرصيد بعد فائدة التوفير: " + savings.getBalance());

// تغيير الاستراتيجية أثناء التشغيل
        savings.setInterestStrategy(new SavingsInterestStrategy(0.05));
        System.out.println("تم تغيير الفائدة إلى 5%");

        savings.applyInterest();
        System.out.println("الرصيد بعد الفائدة الجديدة: " + savings.getBalance());
        System.out.println();

// حساب استثماري
        Account investment = new InvestmentAccount("INV001", "Sara", 5000);
        System.out.println("حساب استثماري" + investment);

        investment.applyInterest();

        System.out.println("رصيد الحساب الاستثماري بعد العائد: "
                + investment.getBalance());

    }
}
