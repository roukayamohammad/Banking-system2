package domain.model;

import domain.entities.Account;
import domain.strategy.StudentInterestStrategy;

public class StudentAccount extends Account {

    private boolean firstDepositBonusGiven = false;

    public StudentAccount(String accountId, String ownerId, double balance) {
        super(accountId, ownerId, balance);
        this.interestStrategy = new StudentInterestStrategy(0.01); // 1% فائدة
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);

        // Bonus مرة واحدة عند أول إيداع
        if (!firstDepositBonusGiven && amount >= 50) {
            this.increaseBalance(5); // مكافأة بسيطة
            firstDepositBonusGiven = true;
            System.out.println("Student bonus: +$5 for first deposit 🎓");
        }
    }

    @Override
    public void withdraw(double amount) {
        // حد سحب يناسب الطلاب (مثلاً 300 في المرة الواحدة)
        if (amount > 300) {
            throw new IllegalArgumentException(
                    "Student account: maximum withdrawal per operation is $300"
            );
        }
        super.withdraw(amount);
    }

    @Override
    public String toString() {
        return super.toString() + " [Student Account]";
    }
}
