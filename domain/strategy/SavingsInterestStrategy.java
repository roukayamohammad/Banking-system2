package domain.strategy;

/**
 * استراتيجية حساب فائدة حساب التوفير
 */
public class SavingsInterestStrategy implements InterestStrategy {

    private final double rate;

    public SavingsInterestStrategy(double rate) {
        this.rate = rate;
    }

    @Override
    public double calculateInterest(double balance) {
        // حساب فائدة التوفير
        return balance * rate;
    }
}
