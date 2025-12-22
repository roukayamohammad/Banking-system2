package domain.chain;

public abstract class TransactionHandler {

    protected TransactionHandler nextHandler;

    public void setNextHandler(TransactionHandler nextHandler) {
        this.nextHandler = nextHandler;
    }


    public abstract boolean approve(TransactionRequest request);
}