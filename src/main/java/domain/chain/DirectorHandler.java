package domain.chain;

import java.util.Scanner;

public class DirectorHandler extends TransactionHandler {

    @Override
    public boolean approve(TransactionRequest request) {
        System.out.println("️ DIRECTOR Approval Required for large amount: $" + request.getAmount());


        Scanner scanner = new Scanner(System.in);
        System.out.print(">>>  Director, please enter Admin PIN (9999) to authorize: ");
        String pin = scanner.next();

        if (pin.equals("9999")) {
            System.out.println(" Director: Authorization Verified. Transaction Approved.");
            return true;
        } else {
            System.out.println(" Director: Authorization Failed! Transaction Denied.");
            return false;
        }
    }
}