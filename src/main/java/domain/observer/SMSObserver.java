package domain.observer;

import domain.entities.Account;

public class SMSObserver implements Observer {

    private String phoneNumber;

    public SMSObserver(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void update(Account account, String message) {

        System.out.println("\n============== SMS ALERT ==============");
        System.out.println(" TO: " + phoneNumber);
        System.out.println(" FROM: Damascus Bank");
        System.out.println(" ------------------------------------------");
        System.out.println(" " + message);
        System.out.println("==========================================\n");
    }
}