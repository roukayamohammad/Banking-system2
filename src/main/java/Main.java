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
import domain.adapter.PaymentProcessor;
import domain.composite.AccountComponent;
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
import domain.observer.SMSObserver;
import domain.security.AuthorizationService;
import domain.security.Permission;
import domain.security.Role;
import domain.report.ReportExporter;
import domain.report.ReportService;

import java.util.List;
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

            Customer currentUser = null;
            if (myAccount != null) {

                currentUser = MockDatabase.getCustomerById(myAccount.getOwnerId());

                System.out.println(" Current User: " + (currentUser != null ? currentUser.getName() : "Unknown")+
                        " | Role: " + (currentUser != null ? currentUser.getRole() : "None"));
                System.out.println(" Account ID: " + myAccount.getAccountId() + " | Account State: " + myAccount.getStateName()+ " | Account Type: "  + myAccount.getClass().getSimpleName());

                System.out.println(" Balance: $" + myAccount.getBalance());
                System.out.println("-----------------------------------------");
            }

            System.out.println("1.   Create New Account (Register Customer)");
            System.out.println("2.   Deposit Money");
            System.out.println("3.   Withdraw Money");
            System.out.println("4.   Show Full Details");
            System.out.println("5.   Change Account State");
            System.out.println("6.   Add Insurance / Overdraft");
            System.out.println("7.   Manage Family Group (Composite)");
            System.out.println("8.   External Transfer (Adapter)");
            System.out.println("9.   Switch Active Account");
            System.out.println("10.  Generate Reports");
            System.out.println("11.  Apply Interest (Strategy)");
            System.out.println("12.   Customer Support");
            System.out.println("0.   Exit");
            System.out.print(">>> Your Choice: ");

            int choice = scanner.nextInt();

            if (myAccount == null && choice != 1 && choice != 0) {
                System.out.println(" Error: Please create an account first.");
                continue;
            }

            try {
                switch (choice) {

                    // ================= CREATE ACCOUNT =================
                    case 1 -> {
                        System.out.print("Enter Customer National ID: ");
                        String custId = scanner.next();

                        if (!MockDatabase.isCustomerExist(custId)) {
                            System.out.print("Name: ");
                            String name = scanner.next();

                            System.out.print("Email: ");
                            String email = scanner.next();

                            System.out.print("Phone: ");
                            String phone = scanner.next();

                            System.out.print("Address: ");
                            String address = scanner.next();

                            System.out.println("Select Role:");
                            System.out.println("1. Customer");
                            System.out.println("2. Teller");
                            System.out.println("3. Manager");
                            System.out.println("4. Admin");
                            System.out.print(">>> ");

                            int r = scanner.nextInt();
                            Role role = switch (r) {
                                case 2 -> Role.TELLER;
                                case 3 -> Role.MANAGER;
                                case 4 -> Role.ADMIN;
                                default -> Role.CUSTOMER;
                            };

                            Customer newCust = new Customer(custId, name, email, phone, address, role);
                            MockDatabase.addCustomer(newCust);
                        }

                        System.out.println("Choose Account Type:");
                        System.out.println("1. Savings | 2. Checking | 3. Investment | 4. Loan");
                        int t = scanner.nextInt();

                        String type = switch (t) {
                            case 1 -> "SAVINGS";
                            case 2 -> "CHECKING";
                            case 3 -> "INVESTMENT";
                            case 4 -> "LOAN";
                            default -> "CHECKING";
                        };

                        String accId = "ACC-" + (int) (Math.random() * 10000);
                        myAccount = AccountFactory.createAccount(type, accId, custId, 0.0);

                        MockDatabase.addAccount(myAccount);

                        myAccount.addObserver(notificationService);
                        myAccount.addObserver(emailService);

                        String userPhone = MockDatabase.getCustomerById(custId).getPhone();
                        myAccount.addObserver(new SMSObserver(userPhone));

                        MockDatabase.getCustomerById(custId).addAccount(myAccount);

                        System.out.println(" Account Created Successfully!");
                    }

                    // ================= DEPOSIT =================
                    case 2 -> {
                        AuthorizationService.checkPermission(currentUser, Permission.PROCESS_TRANSACTION);
                        System.out.print("Deposit Amount: ");
                        myAccount.deposit(scanner.nextDouble());
                    }

                    // ================= WITHDRAW =================
                    case 3 -> {
                        AuthorizationService.checkPermission(currentUser, Permission.PROCESS_TRANSACTION);
                        System.out.print("Withdraw Amount: ");
                        myAccount.withdraw(scanner.nextDouble());
                    }

                    // ================= DETAILS =================
                    case 4 -> {
                        System.out.println(myAccount);
                        System.out.println("State: " + myAccount.getStateName());
                    }

                    // ================= STATE =================
                    case 5 -> {
                        System.out.println("\n--- Account State Management ---");
                        System.out.println("1. Freeze");
                        System.out.println("2. Suspend");
                        System.out.println("3. Activate");
                        System.out.println("4. Close");
                        System.out.print(">>> ");

                        int s = scanner.nextInt();
                        switch (s) {
                            case 1 -> myAccount.freeze();
                            case 2 -> myAccount.suspend();
                            case 3 -> myAccount.activate();
                            case 4 -> myAccount.close();
                            default -> System.out.println("Invalid option.");
                        }
                    }

                    // ================= DECORATOR =================
                    case 6 -> {
                        System.out.println("1. Overdraft | 2. Insurance");
                        int d = scanner.nextInt();

                        if (d == 1)
                            myAccount = new OverdraftProtectionDecorator(myAccount, 1000);
                        else
                            myAccount = new InsuranceDecorator(myAccount, 50000, 50);
                    }

                    // ================= COMPOSITE =================
                    case 7 -> {
                        System.out.println("\n--- Family Group (Composite) ---");
                        System.out.println("1. Create group");
                        System.out.println("2. Add account");
                        System.out.println("3. Remove account");
                        System.out.println("4. Show hierarchy");
                        System.out.println("5. Total balance");
                        System.out.println("6. Deposit to group");
                        System.out.println("7. Withdraw from group");
                        System.out.print(">>> ");

                        int g = scanner.nextInt();

                        switch (g) {
                            case 1 -> {
                                System.out.print("Group name: ");
                                familyGroup = new AccountGroup(scanner.next());
                            }
                            case 2 -> {
                                if (familyGroup == null) break;
                                familyGroup.add(new SingleAccount(myAccount));
                            }
                            case 3 -> {
                                if (familyGroup == null) break;
                                List<AccountComponent> list = familyGroup.getChildren();
                                for (int i = 0; i < list.size(); i++)
                                    System.out.println((i + 1) + ". " + list.get(i).getName());
                                System.out.print("Choose: ");
                                int idx = scanner.nextInt() - 1;
                                if (idx >= 0 && idx < list.size())
                                    familyGroup.remove(list.get(idx));
                            }
                            case 4 -> {
                                if (familyGroup != null) familyGroup.display(0);
                            }
                            case 5 -> {
                                if (familyGroup != null)
                                    System.out.println("Total: $" + familyGroup.getBalance());
                            }
                            case 6 -> {
                                if (familyGroup == null) break;
                                System.out.print("Amount: ");
                                familyGroup.deposit(scanner.nextDouble());
                            }
                            case 7 -> {
                                if (familyGroup == null) break;
                                System.out.print("Amount: ");
                                familyGroup.withdraw(scanner.nextDouble());
                            }
                        }
                    }

                    // ================= ADAPTER =================
                    case 8 -> {
                        System.out.print("Enter amount: ");
                        double ext = scanner.nextDouble();

                        if (ext > myAccount.getBalance()) {
                            System.out.println("Insufficient balance.");
                            break;
                        }

                        myAccount.withdraw(ext);

                        PaymentProcessor processor = new BankAdapter(new ExternalBank());
                        processor.pay(ext);

                        System.out.println("External transfer completed.");
                    }

                    // ================= SWITCH ACCOUNT =================
                    case 9 -> {
                        List<Account> all = MockDatabase.getAllAccounts();
                        for (int i = 0; i < all.size(); i++) {
                            Customer o = MockDatabase.getCustomerById(all.get(i).getOwnerId());
                            System.out.println((i + 1) + ". " + all.get(i).getAccountId() +
                                    " | Owner: " + o.getName() +
                                    " | Balance: $" + all.get(i).getBalance());
                        }
                        System.out.print("Choose: ");
                        int idx = scanner.nextInt() - 1;
                        if (idx >= 0 && idx < all.size())
                            myAccount = all.get(idx);
                    }

                    // ================= REPORTS =================
                    case 10 -> {
                        System.out.println("1. Daily");
                        System.out.println("2. Summary");
                        System.out.println("3. Audit");
                        System.out.println("4. Export");
                        System.out.print(">>> ");

                        int r = scanner.nextInt();
                        switch (r) {
                            case 1 -> ReportService.dailyTransactions();
                            case 2 -> ReportService.accountSummary();
                            case 3 -> ReportService.auditLogs();
                            case 4 -> ReportExporter.exportDailyTransactions("daily.txt");
                        }
                    }

                    // ================= STRATEGY =================
                    case 11 -> {
                        myAccount.applyInterest();
                        System.out.println("New Balance: $" + myAccount.getBalance());
                    }
                    // ================= SUPPORT SYSTEM =================
                    case 12 -> {
                        System.out.println("\n--- 🎫 Customer Support Center ---");
                        System.out.println("1. Submit a Complaint/Query");
                        System.out.println("2. View All Tickets (Admin/Manager Only)");
                        System.out.print(">>> Select: ");
                        int sup = scanner.nextInt();

                        if (sup == 1) {
                            System.out.print("Describe your issue: ");
                            scanner.nextLine();
                            String issue = scanner.nextLine();


                            MockDatabase.addTicket(new domain.entities.SupportTicket(
                                    currentUser.getCustomerId(), issue
                            ));
                        } else if (sup == 2) {

                            AuthorizationService.checkPermission(currentUser, Permission.MANAGE_USERS);

                            System.out.println("\n📋 --- All Support Tickets ---");
                            var tickets = MockDatabase.getAllTickets();
                            if(tickets.isEmpty()) System.out.println("No tickets found.");
                            else tickets.forEach(System.out::println);
                        }
                    }

                    case 0 -> {
                        System.out.println("Goodbye!");
                        return;
                    }

                    default -> System.out.println("Invalid option.");
                }

            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
}
