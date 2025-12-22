package domain.chain;

public class AutoApprovalHandler extends TransactionHandler {

    @Override
    public boolean approve(TransactionRequest request) {
        if (request.getAmount() <= 1000) {
            System.out.println(" Auto System: Transaction of $" + request.getAmount() + " approved automatically.");
            return true;
        } else {
            if (nextHandler != null) {
                System.out.println(" Auto System: Too high for auto. Forwarding to Manager...");
                return nextHandler.approve(request);
            }
            return false;
        }
    }
}