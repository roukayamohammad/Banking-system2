package domain.test;

import domain.chain.*;

public class ChainTest {
    public static void main(String[] args) {
        System.out.println("\n=== 🔗 Chain of Responsibility Pattern Test ===\n");


        TransactionHandler autoSystem = new AutoApprovalHandler();
        TransactionHandler manager = new ManagerHandler();
        TransactionHandler director = new DirectorHandler();


        autoSystem.setNextHandler(manager);
        manager.setNextHandler(director);




        System.out.println("\n--- Request 1: $500 ---");
        autoSystem.approve(new TransactionRequest("ACC001", 500, "Withdraw"));


        System.out.println("\n--- Request 2: $5,000 ---");
        autoSystem.approve(new TransactionRequest("ACC001", 5000, "Withdraw"));


        System.out.println("\n--- Request 3: $50,000 ---");
        autoSystem.approve(new TransactionRequest("ACC001", 50000, "Withdraw"));
    }
}