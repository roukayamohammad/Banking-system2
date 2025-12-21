package domain.strategy;

/**
 * استراتيجية حساب فائدة القرض
 */
public class LoanInterestStrategy implements InterestStrategy {


    private final double annualRate;

    public LoanInterestStrategy(double annualRate) {
        this.annualRate = annualRate;
    }

    @Override
    public double calculateInterest(double balance) {
        if (balance >= 0) return 0;
        return -balance * (annualRate / 12);
    }

    public double getAnnualRate() {
        return annualRate;
    }
}

