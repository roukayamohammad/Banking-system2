package domain.strategy;


public class StudentInterestStrategy implements InterestStrategy {

    private final double rate;

    public StudentInterestStrategy(double rate) {
        this.rate = rate;
    }

    @Override
    public double calculateInterest(double balance) {
        // فائدة بسيطة وثابتة
        return balance * rate;
    }
}
