package domain.factory;

import domain.entities.Account;
import domain.model.CheckingAccount;
import domain.model.InvestmentAccount;
import domain.model.LoanAccount;
import domain.model.SavingsAccount;

import java.util.HashMap;
import java.util.Map;

public class AccountFactory {

    private static Map<String, AccountCreator> registry = new HashMap<>();

    // تسجيل الأنواع مرة واحدة
    static {
        registry.put("SAVINGS",
                (id, owner, balance) -> new SavingsAccount(id, owner, balance));

        registry.put("CHECKING",
                (id, owner, balance) -> new CheckingAccount(id, owner, balance));

        registry.put("LOAN",
                (id, owner, balance) -> new LoanAccount(id, owner, balance));

        registry.put("INVESTMENT",
                (id, owner, balance) -> new InvestmentAccount(id, owner, balance));
//        registry.put("STUDENT",
//                (id, owner, balance) -> new StudentAccount(id, owner, balance));

    }

    public static Account createAccount(String type,
                                        String accountId,
                                        String ownerId,
                                        double balance) {

        AccountCreator creator = registry.get(type.toUpperCase());

        if (creator == null) {
            throw new IllegalArgumentException("Unknown account type: " + type);
        }

        return creator.create(accountId, ownerId, balance);
    }
}
