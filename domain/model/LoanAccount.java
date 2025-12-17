package domain.model;

import domain.entities.Account;

public class LoanAccount extends Account {
    private double loanInterestRate;
    private double originalLoanAmount;
    private int loanTermMonths;
    private int monthsPaid;


    public LoanAccount(String accountId, String ownerId, double loanAmount) {
        this(accountId, ownerId, loanAmount, 0.05, 60); // 5% فائدة، 60 شهر
    }

    public LoanAccount(String accountId, String ownerId, double loanAmount,
                       double interestRate, int termMonths) {
        // رصيد القرض يكون سالب (المبلغ المقترض)
        super(accountId, ownerId, -loanAmount);
        this.loanInterestRate = interestRate;
        this.originalLoanAmount = loanAmount;
        this.loanTermMonths = termMonths;
        this.monthsPaid = 0;
    }

    public void makePayment(double amount) {
        // في القرض، الإيداع هو سداد القرض
        super.deposit(amount); // يقلل الرصيد السالب
        monthsPaid++;
        System.out.printf("Loan payment: $%.2f | Remaining: $%.2f%n",
                amount, -balance);

        // إضافة فائدة شهرية
        if (balance < 0) { // إذا بقي قرض
            double monthlyInterest = -balance * (loanInterestRate / 12);
            balance -= monthlyInterest; // يزيد الدين
            System.out.printf("Monthly interest added: $%.2f%n", monthlyInterest);
        }
    }

    public double getMonthlyPayment() {
        // حساب القسط الشهري
        double monthlyRate = loanInterestRate / 12;
        double payment = originalLoanAmount *
                (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths)) /
                (Math.pow(1 + monthlyRate, loanTermMonths) - 1);
        return payment;
    }

    public double getLoanInterestRate() {
        return loanInterestRate;
    }

    public int getRemainingMonths() {
        return loanTermMonths - monthsPaid;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [Loan, Rate: %.2f%%, Remaining: %d months]",
                loanInterestRate * 100, getRemainingMonths());
    }
}
