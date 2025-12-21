package domain.adapter;

public class BankAdapter implements PaymentProcessor {
    private ExternalBank external;

    public BankAdapter(ExternalBank external) {
        this.external = external;
    }

    @Override
    public void pay(double amount) {
        external.sendMoney(amount);
    }

}
