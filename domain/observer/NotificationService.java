package domain.observer;

import domain.entities.Account;

public class NotificationService implements Observer {

    @Override
    public void update(Account account, String message) {

        System.out.println(">>> Notification: Account " + account.getAccountId() + " says: " + message);
    }
}