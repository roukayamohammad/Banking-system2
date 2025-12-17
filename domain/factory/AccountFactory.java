package domain.factory;

import domain.entite.Account;
import domain.model.CheckingAccount;
import domain.model.InvestmentAccount;
import domain.model.LoanAccount;
import domain.model.SavingsAccount;

public class AccountFactory {

    public static Account createAccount(String type, String accountId, String ownerId, double balance) {
        switch (type.toUpperCase()) {
            case "SAVINGS":
                return new SavingsAccount(accountId, ownerId, balance, 0.02); // 2% فائدة
            case "CHECKING":
                return new CheckingAccount(accountId, ownerId, balance, 5.0); // رسوم شهرية 5
            case "LOAN":
                return new LoanAccount(accountId, ownerId, balance, 0.05, 10); // فائدة قرض 5%
            case "INVESTMENT":
//                return new InvestmentAccount(accountId, ownerId, balance, 0.04); // 4% استثمار
            default:
                throw new IllegalArgumentException("Unknown account type: " + type);
        }
    }
}
