package domain.strategy;

/**
 * استراتيجية حساب عائد الاستثمار
 */
public class InvestmentInterestStrategy implements InterestStrategy {

    private final double annualRate;

    public InvestmentInterestStrategy(double annualRate) {
        this.annualRate = annualRate;
    }

    @Override
    public double calculateInterest(double balance) {
        // حساب العائد السنوي
        return balance * annualRate;
    }
}

