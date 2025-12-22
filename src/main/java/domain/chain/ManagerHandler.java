package domain.chain;

import java.util.Scanner;

public class ManagerHandler extends TransactionHandler {

    @Override
    public boolean approve(TransactionRequest request) {
        if (request.getAmount() <= 10000) {

            System.out.println(" Manager Approval Required for amount: $" + request.getAmount());


            Scanner scanner = new Scanner(System.in);
            System.out.print(">>>  Manager, please enter PIN (1111) to confirm: ");
            String pin = scanner.next();

            if (pin.equals("1111")) {
                System.out.println(" Manager: PIN Correct. Transaction Approved.");
                return true;
            } else {
                System.out.println(" Manager: Wrong PIN! Transaction Rejected.");
                return false;
            }

        } else {

            if (nextHandler != null) {
                System.out.println(" Manager: $" + request.getAmount() + " is too high for me. Forwarding to Director...");
                return nextHandler.approve(request);
            }
            return false;
        }
    }
}