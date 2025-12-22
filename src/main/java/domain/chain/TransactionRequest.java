package domain.chain;

    public class TransactionRequest {
        private double amount;
        private String type;
        private String accountId;

        public TransactionRequest(String accountId, double amount, String type) {
            this.accountId = accountId;
            this.amount = amount;
            this.type = type;
        }

        public double getAmount() {
            return amount;
        }

        public String getType() {
            return type;
        }

        public String getAccountId() {
            return accountId;
        }
    }

