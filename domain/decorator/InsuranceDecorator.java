package domain.decorator;

import domain.entities.Account;

public class InsuranceDecorator extends AccountDecorator {

    private double coverageAmount;
    private double monthlyPremium;
    private boolean isActive;

    public InsuranceDecorator(Account account,
                              double coverageAmount,
                              double monthlyPremium) {
        super(account);
        this.coverageAmount = coverageAmount;
        this.monthlyPremium = monthlyPremium;
        this.isActive = true;
    }
// تأمين
    public double getEffectiveBalance() {
        return decoratedAccount.getBalance() +
                (isActive ? coverageAmount : 0);
    }
//    قسط شهري يتم خصمه
    public void applyMonthlyPremium() {
        if (isActive) {
            decoratedAccount.withdraw(monthlyPremium);
            System.out.println("Insurance premium applied: $" + monthlyPremium);
        }
    }

//    مطالبة تأمين تعويض مالي
    public boolean fileClaim(double amount) {
        if (isActive && amount <= coverageAmount) {
            decoratedAccount.deposit(amount);
            System.out.println("Insurance claim paid: $" + amount);
            return true;
        }
        return false;
    }

    @Override
    public String getFeatures() {
        return "Insurance Coverage [Amount: $" + coverageAmount +
                ", Premium: $" + monthlyPremium + "]";
    }
}
