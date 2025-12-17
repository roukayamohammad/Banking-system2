package domain.factory;

import domain.entities.Account;

public interface AccountCreator {
    Account create(String accountId, String ownerId, double balance);
}
