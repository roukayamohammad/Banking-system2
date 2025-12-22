//import domain.factory.SavingAccountFactory;


/*import domain.decorator.InsuranceDecorator;
import domain.decorator.OverdraftProtectionDecorator;
import domain.entities.Account;
import domain.entities.Customer;
import domain.factory.AccountFactory;
import domain.model.CheckingAccount;
import domain.model.LoanAccount;
import domain.model.SavingsAccount;
import domain.entities.Account;
import domain.factory.AccountFactory;
import domain.observer.NotificationService;

public class Main {
    public static void main(String[] args) {*/

        //   Account s1 = new SavingsAccount("A1", "U1", 10000);
        //     Account c1 = new CheckingAccount("A2", "U1", 500);

        // s1.internalDeposit(0);
        // AccountGroup family = new AccountGroup("G1", "U1");
        // family.add(s1);
        // family.add(c1);

        // family.deposit(100); // يوزّع 100 لكل حساب
        // System.out.println(s1.getBalance()); // 1100
        // System.out.println(c1.getBalance()); // 600


        // s1.setState(new ActiveState());
        // try {

        //     s1.withdraw(50);
        //      // يرمي استثناء لأن الحساب مجمّد
        //      System.out.println("hhhhh");
        // } catch (Exception e) {
        //     System.out.println(e.getMessage()); // Account is frozen
        // }

//Account c1=new
//        Account c1 = new Account("A1", "U1", 2200);
//        System.out.println(c1.getBalance());
//        c1.deposit(99);
//        c1.getStateName();
//
//        System.out.println(c1.getStateName());
//        System.out.println(c1.getBalance());
/*
تطبيق لل composit هون تحت 

*//*

//        Account Account2 = new Account("A2", "U1", 200) {
//
//            @Override
//            public String getType() {
//                // TODO Auto-generated method stub
//                throw new UnsupportedOperationException("Unimplemented method 'getType'");
//            }
//
//        };


//        Account Account3 = new Account("A3", "U1", 200) {
//
//            @Override
//            public String getType() {
//                // TODO Auto-generated method stub
//                throw new UnsupportedOperationException("Unimplemented method 'getType'");
//            }
//
//        };
//
//        AccountComponent accountComponent = new SingelAccount(Account2);
//        AccountComponent accountComponent2 = new SingelAccount(Account3);
//
//        AccountGroup node1 = new AccountGroup("This node one ");
//        node1.add(accountComponent)
//        ;
//        node1.add(accountComponent2)
//        ;
//
//        node1.deposit(200);
//        System.out.println(node1.getBalance());



*//*

هون تطبيق على ال  factory



*//*


//
//AccountFactory factory=new SavingAccountFactory();
//Account acc=factory.creatAccount("A4", "U1", 33, new ActiveState())
//;
//
//System.out.println(acc.getStateName());
//System.out.println(acc.getBalance());
//System.out.println(acc.getType());

*/






/*import domain.adapter.BankAdapter;
import domain.adapter.ExternalBank;
import domain.composite.AccountGroup;
import domain.composite.SingelAccount;
import domain.decorator.InsuranceDecorator;
import domain.decorator.OverdraftProtectionDecorator;
import domain.entities.Account;
import domain.factory.AccountFactory;
import domain.observer.NotificationService;
import domain.observer.RealEmailObserver;

import java.util.Scanner;

        public class Main {
            public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);


                NotificationService notificationService = new NotificationService();


                Account myAccount = null;
                AccountGroup familyGroup = null;

                System.out.println("=========================================");
                System.out.println("   DAMASCUS UNIVERSITY BANKING SYSTEM    ");
                System.out.println("   (Software Engineering 3 Project)      ");
                System.out.println("=========================================");

                while (true) {
                    System.out.println("\n--- MAIN MENU (SELECT AN OPERATION) ---");
                    System.out.println("1.  [Factory] Create New Account");
                    System.out.println("2.  [Observer + State] Deposit Money");
                    System.out.println("3.  [Observer + State] Withdraw Money");
                    System.out.println("4.  Show Account Info & Balance");
                    System.out.println("5.  [State] Freeze/Unfreeze Account");
                    System.out.println("6.  [Decorator] Add Features (Insurance/Overdraft)");
                    System.out.println("7.  [Composite] Manage Account Groups (Family)");
                    System.out.println("8.  [Adapter] External Transfer (Legacy Bank)");
                    System.out.println("0.  Exit");
                    System.out.print(">>> Your Choice: ");

                    int choice = scanner.nextInt();


                    if (myAccount == null && (choice >= 2 && choice <= 6)) {
                        System.out.println(" Error: No account found! Please create an account first (Option 1).");
                        continue;
                    }

                    try {
                        switch (choice) {
                            case 1:
                                System.out.println("\n--- Create Account ---");
                                System.out.println("Types: 1.Savings | 2.Checking | 3.Investment | 4.Loan");
                                System.out.print("Select Type: ");
                                int typeIn = scanner.nextInt();
                                String type = switch (typeIn) {
                                    case 1 -> "SAVINGS";
                                    case 2 -> "CHECKING";
                                    case 3 -> "INVESTMENT";
                                    case 4 -> "LOAN";
                                    default -> "CHECKING";
                                };


                              //  myAccount = AccountFactory.createAccount(type, "ACC-" + System.currentTimeMillis() % 1000, "Student-User", 0.0);

                                myAccount = AccountFactory.createAccount(type, "ACC-" + System.currentTimeMillis() % 1000, "101", 0.0);

                                myAccount.addObserver(notificationService);

                                RealEmailObserver emailService = new RealEmailObserver();
                                myAccount.addObserver(emailService);

                                System.out.println(" Account Created Successfully via Factory!");
                                break;

                            case 2:
                                System.out.print("Enter amount to deposit: ");
                                double depAmount = scanner.nextDouble();
                                myAccount.deposit(depAmount);
                                break;

                            case 3:
                                System.out.print("Enter amount to withdraw: ");
                                double withAmount = scanner.nextDouble();
                                myAccount.withdraw(withAmount);
                                break;

                            case 4:
                                System.out.println("\n---------------- ACCOUNT DETAILS ----------------");
                                if(myAccount != null) {
                                    System.out.println("Account: " + myAccount.toString());

                                }
                                if (familyGroup != null) {
                                    System.out.println("\n[Composite Group Info]");
                                    familyGroup.display(0);
                                    System.out.println("Total Group Balance: " + familyGroup.getBalance());
                                }
                                System.out.println("-------------------------------------------------");
                                break;

                            case 5: // === STATE PATTERN ===
                                System.out.println("\n--- State Management ---");
                                System.out.println("Current State: " + myAccount.getStateName());
                                if (myAccount.getStateName().equalsIgnoreCase("ACTIVE")) {
                                    System.out.println("Action: Freezing account...");
                                    myAccount.freeze();
                                } else if (myAccount.getStateName().equalsIgnoreCase("FROZEN")) {
                                    System.out.println("Action: Activating account...");
                                    myAccount.activate();
                                } else {
                                    System.out.println("Account is " + myAccount.getStateName() + " and cannot be toggled easily.");
                                }
                                break;

                            case 6:
                                System.out.println("\n--- Add Extra Services (Decorator) ---");
                                System.out.println("1. Overdraft Protection (Allow negative balance)");
                                System.out.println("2. Insurance (Protect funds)");
                                System.out.print("Select: ");
                                int decChoice = scanner.nextInt();

                                if (decChoice == 1) {

                                    myAccount = new OverdraftProtectionDecorator(myAccount, 1000.0);
                                    System.out.println(" Overdraft Protection added (Limit: 1000)!");
                                } else if (decChoice == 2) {

                                    myAccount = new InsuranceDecorator(myAccount, 50000.0, 50.0);
                                    System.out.println(" Insurance Policy added!");
                                }
                                break;

                            case 7:
                                System.out.println("\n--- Account Groups (Composite) ---");
                                if (familyGroup == null) {
                                    familyGroup = new AccountGroup("My Family Group");
                                    System.out.println("Created new group: 'My Family Group'");
                                }


                                SingelAccount leafAccount = new SingelAccount(myAccount);
                                familyGroup.add(leafAccount);

                                System.out.println(" Added current account (" + myAccount.getAccountId() + ") to the group.");
                                System.out.println("Group Total Balance is now: " + familyGroup.getBalance());
                                break;

                            case 8:
                                System.out.println("\n--- External Transfer (Adapter) ---");
                                System.out.print("Enter amount to receive from External Bank: ");
                                double extAmount = scanner.nextDouble();


                                ExternalBank externalSystem = new ExternalBank();

                                BankAdapter adapter = new BankAdapter(externalSystem);


                                adapter.pay(extAmount);


                                System.out.println("(System): Receiving funds from adapter...");
                                myAccount.deposit(extAmount);
                                System.out.println(" External transfer completed.");
                                break;

                            case 0:
                                System.out.println("Goodbye!");
                                return;

                            default:
                                System.out.println("Invalid option.");
                        }
                    } catch (Exception e) {
                        System.out.println(" Exception Occurred: " + e.getMessage());
                    }
                }
            }
        }*/

import domain.adapter.BankAdapter;
import domain.adapter.ExternalBank;
import domain.composite.AccountGroup;
import domain.composite.SingleAccount;
import domain.decorator.InsuranceDecorator;
import domain.decorator.OverdraftProtectionDecorator;
import domain.entities.Account;
import domain.entities.Customer;
import domain.entities.MockDatabase;
import domain.factory.AccountFactory;
import domain.observer.NotificationService;
import domain.observer.RealEmailObserver;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.println("=========================================");
        System.out.println("    DAMASCUS BANK - ADMIN SETUP        ");
        System.out.println("=========================================");
        System.out.println("Please enter Bank SMTP Credentials to enable alerts:");

        System.out.print(">>> Bank Email (Gmail): ");

        String bankEmail = scanner.nextLine();

        System.out.print(">>> App Password (16 chars): ");

        String bankPass = scanner.nextLine();


        RealEmailObserver emailService = new RealEmailObserver(bankEmail, bankPass);
        NotificationService notificationService = new NotificationService();
        Account myAccount = null;
        AccountGroup familyGroup = null;


        while (true) {
            System.out.println("\n=========================================");
            System.out.println("         MAIN BANKING MENU           ");
            System.out.println("=========================================");


            if (myAccount != null) {
                Customer owner = MockDatabase.getCustomerById(myAccount.getOwnerId());
                System.out.println(" Current User: " + (owner != null ? owner.getName() : "Unknown"));
                System.out.println(" Account ID: " + myAccount.getAccountId() + " | Type: " + myAccount.getStateName());
                System.out.println(" Balance: $" + myAccount.getBalance());
                System.out.println("-----------------------------------------");
            }

            System.out.println("1.   Create New Account (Register Customer)");
            System.out.println("2.   Deposit Money");
            System.out.println("3.   Withdraw Money");
            System.out.println("4.   Show Full Details");
            System.out.println("5.   Freeze/Unfreeze Account");
            System.out.println("6.   Add Insurance / Overdraft");
            System.out.println("7.   Manage Family Group (Composite)");
            System.out.println("8.   External Transfer (Adapter)");
            System.out.println("0.   Exit");
            System.out.print(">>> Your Choice: ");

            int choice = scanner.nextInt();


            if (myAccount == null && choice != 1 && choice != 0) {
                System.out.println(" Error: No account active! Please select Option 1 first.");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        System.out.println("\n---  CLIENT REGISTRATION & ACCOUNT CREATION ---");


                        System.out.print("Enter Customer National ID: ");
                        String custId = scanner.next();


                        if (!MockDatabase.isCustomerExist(custId)) {
                            System.out.println(">>> New Customer Detected! Please enter details:");

                            System.out.print("Name: ");
                            String name = scanner.next();

                            System.out.print("Email (Real one for alerts): ");
                            String email = scanner.next();

                            System.out.print("Phone: ");
                            String phone = scanner.next();

                            System.out.print("Address: ");
                            String address = scanner.next();


                            Customer newCust = new Customer(custId, name, email, phone, address);
                            MockDatabase.addCustomer(newCust);
                        } else {
                            System.out.println(">>> Welcome back, " + MockDatabase.getCustomerById(custId).getName());
                        }


                        System.out.println("\nChoose Account Type:");
                        System.out.println("1. Savings | 2. Checking | 3. Investment | 4. Loan");
                        System.out.print(">>> Select: ");
                        int typeIn = scanner.nextInt();
                        String type = switch (typeIn) {
                            case 1 -> "SAVINGS";
                            case 2 -> "CHECKING";
                            case 3 -> "INVESTMENT";
                            case 4 -> "LOAN";
                            default -> "CHECKING";
                        };


                        String accId = "ACC-" + (int)(Math.random() * 10000);
                        myAccount = AccountFactory.createAccount(type, accId, custId, 0.0);


                        myAccount.addObserver(notificationService);
                        myAccount.addObserver(emailService);

                        MockDatabase.getCustomerById(custId).addAccount(myAccount);

                        System.out.println(" Account Created Successfully & Linked to Customer!");
                        break;

                    case 2:
                        System.out.print("Enter amount to deposit: ");
                        double depAmount = scanner.nextDouble();
                        myAccount.deposit(depAmount);
                        break;

                    case 3:
                        System.out.print("Enter amount to withdraw: ");
                        double withAmount = scanner.nextDouble();
                        myAccount.withdraw(withAmount);
                        break;

                    case 4:
                        System.out.println("\n---------------- FULL REPORT ----------------");

                        Customer c = MockDatabase.getCustomerById(myAccount.getOwnerId());
                        System.out.println(c.toString());
                        System.out.println("Current Active Account State: " + myAccount.getStateName());

                        if (familyGroup != null) {
                            System.out.println("\n[Family Group Hierarchy]");
                            familyGroup.display(0);
                            System.out.println("Total Family Balance: $" + familyGroup.getBalance());
                        }
                        System.out.println("---------------------------------------------");
                        break;

                    case 5:
                        System.out.println("\n--- State Control ---");
                        String state = myAccount.getStateName();
                        if (state.equalsIgnoreCase("ACTIVE")) {
                            System.out.print("Freeze this account? (1: Yes, 0: No): ");
                            if (scanner.nextInt() == 1) myAccount.freeze();
                        } else if (state.equalsIgnoreCase("FROZEN")) {
                            System.out.print("Unfreeze/Activate this account? (1: Yes, 0: No): ");
                            if (scanner.nextInt() == 1) myAccount.activate();
                        } else {
                            System.out.println("Current state (" + state + ") doesn't allow manual toggle here.");
                        }
                        break;

                    case 6:
                        System.out.println("\n--- Add Features (Decorator) ---");
                        System.out.println("1. Overdraft Protection (Limit: $1000)");
                        System.out.println("2. Insurance Policy (Cost: $50/mo)");
                        System.out.print("Select: ");
                        int dec = scanner.nextInt();
                        if (dec == 1) {
                            myAccount = new OverdraftProtectionDecorator(myAccount, 1000.0);
                            System.out.println(" Overdraft feature applied.");
                        } else if (dec == 2) {
                            myAccount = new InsuranceDecorator(myAccount, 50000.0, 50.0);
                            System.out.println(" Insurance applied.");
                        }
                        break;

                    case 7:
                        System.out.println("\n--- Family Group (Composite) ---");
                        if (familyGroup == null) {
                            familyGroup = new AccountGroup("Family Fund");
                        }

                        familyGroup.add(new SingleAccount(myAccount));
                        System.out.println(" Current account added to 'Family Fund'.");
                        System.out.println("Group Total: $" + familyGroup.getBalance());
                        break;

                    case 8:
                        System.out.println("\n--- External Bank Transfer ---");
                        System.out.print("Amount coming from external bank: ");
                        double extVal = scanner.nextDouble();

                        ExternalBank extBank = new ExternalBank();
                        BankAdapter adapter = new BankAdapter(extBank);

                        System.out.println("Processing via Adapter...");
                        adapter.pay(extVal);
                        myAccount.deposit(extVal);
                        break;

                    case 0:
                        System.out.println("Exiting System. Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid Option.");
                }
            } catch (Exception e) {
                System.out.println(" Error: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
}

