
import domain.adapter.BankAdapter;
import domain.adapter.ExternalBank;
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
import domain.report.ReportExporter;
import domain.report.ReportService;
import domain.security.AuthorizationService;
import domain.security.Permission;
import domain.security.Role;

import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    static Customer currentUser;
    static Account currentAccount;

    static NotificationService notificationService = new NotificationService();

    static RealEmailObserver emailService = new RealEmailObserver(
            "tukaalshallah2000@gmail.com",
            "lksu blhe kdqs wqhd"
    );

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("        DAMASCUS BANK SYSTEM          ");
        System.out.println("=====================================");

        while (true) {
            loginOrRegister();

            currentAccount = currentUser.getAccounts().isEmpty()
                    ? null
                    : currentUser.getAccounts().get(0);

            boolean backToLogin = false;

            while (!backToLogin) {
                showMenuByRole();
                int choice = scanner.nextInt();
                backToLogin = handleChoice(choice);
            }
        }
    }
    // ================= LOGIN =================
    static void loginOrRegister() {

        System.out.print("Enter National ID: ");
        String id = scanner.next();

        if (!MockDatabase.isCustomerExist(id)) {
            createUserAndAccount(id);
        }

        currentUser = MockDatabase.getCustomerById(id);

        if (currentUser.getRole() == Role.ADMIN) {
            System.out.println("Welcome ADMIN: " + currentUser.getName());
            return;
        }

        if (currentUser.getAccounts().isEmpty()) {
            System.out.println("No account found. Creating new account...");
            createAccountForExistingUser(currentUser);
        }

        currentAccount = currentUser.getAccounts().get(0);

        System.out.println(
                "Welcome " + currentUser.getName() + " | Role: " + currentUser.getRole()
        );
    }

    static void createAccountForExistingUser(Customer customer) {

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

        Account acc = AccountFactory.createAccount(
                type, accId, customer.getCustomerId(), 0
        );

        acc.addObserver(notificationService);
        acc.addObserver(emailService);
        acc.addObserver(new SMSObserver(customer.getPhone()));

        customer.addAccount(acc);

        System.out.println("Account created and linked successfully.");
    }


    static void createUserAndAccount(String id) {

        System.out.println("New User Registration");

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

        int r = scanner.nextInt();

        Role role = switch (r) {
            case 2 -> Role.TELLER;
            case 3 -> Role.MANAGER;
            default -> Role.CUSTOMER;
        };

        Customer customer = new Customer(id, name, email, phone, address, role);
        MockDatabase.addCustomer(customer);

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
        Account acc = AccountFactory.createAccount(type, accId, id, 0);

        acc.addObserver(notificationService);
        acc.addObserver(emailService);
        acc.addObserver(new SMSObserver(phone));

        customer.addAccount(acc);

        System.out.println("Account Created Successfully");
    }

    static void showMenuByRole() {

        System.out.println("\n========= MAIN MENU =========");
        System.out.println("1. View Account Details");

        if (currentUser.getRole() == Role.CUSTOMER) {
            System.out.println("2. Manage My Accounts (Composite)");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. External Transfer");
            System.out.println("6. Customer Support");
        }

        if (has(Permission.APPROVE_TRANSACTION)) {
            System.out.println("7. Change Account State");
        }

        if (has(Permission.VIEW_REPORTS)) {
            System.out.println("8. Reports");
        }

        if (has(Permission.MANAGE_USERS)) {
            System.out.println("6. Customer Support (View Tickets)");
            System.out.println("9. Add Account Features");
        }

        if (has(Permission.VIEW_SYSTEM_STATS)) {
            System.out.println("10. Apply Interest");
        }

        System.out.println("0. Logout");
        System.out.print(">>> ");
    }


    static void handleCustomerAccounts() {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- Manage My Accounts ---");
            System.out.println("1. View My Accounts");
            System.out.println("2. Add Account to Family Group");
            System.out.println("3. Remove Account from Family Group");
            System.out.println("4. Create New Account");
            System.out.println("5. Create New Family Group");
            System.out.println("6. Show hierarchy");
            System.out.println("7. Total balance");
            System.out.println("8. Deposit to group");
            System.out.println("9. Withdraw from group");
            System.out.println("0. Back");
            System.out.print(">>> ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1 -> {
                    for (Account acc : currentUser.getAccounts()) {
                        System.out.println(acc);
                    }
                }

                case 2 -> {
                    AccountGroup group = selectFamilyGroup();
                    if (group == null) break;

                    System.out.print("Enter Account ID to add: ");
                    String accId = scanner.nextLine();

                    Account acc = currentUser.getAccounts().stream()
                            .filter(a -> a.getAccountId().equals(accId))
                            .findFirst()
                            .orElse(null);

                    if (acc == null) {
                        System.out.println("Account not found.");
                    } else {
                        group.add(new SingleAccount(acc));
                        System.out.println("Account added successfully.");
                    }
                }

                case 3 -> {
                    AccountGroup group = selectFamilyGroup();
                    if (group == null) break;

                    List<AccountComponent> children = group.getChildren();
                    for (int i = 0; i < children.size(); i++) {
                        System.out.println((i + 1) + ". " + children.get(i).getName());
                    }

                    System.out.print("Select account to remove: ");
                    int idx = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (idx >= 0 && idx < children.size()) {
                        group.remove(children.get(idx));
                        System.out.println("Account removed.");
                    }
                }

                case 4 -> createAccountForExistingUser(currentUser);

                case 5 -> {
                    System.out.print("Enter Family Group Name: ");
                    String name = scanner.nextLine();
                    currentUser.addFamilyGroup(new AccountGroup(name));
                    System.out.println("Family Group created.");
                }

                case 6 -> {
                    AccountGroup group = selectFamilyGroup();
                    if (group != null) group.display(0);
                }

                case 7 -> {
                    AccountGroup group = selectFamilyGroup();
                    if (group != null)
                        System.out.println("Total Balance: $" + group.getBalance());
                }

                case 8 -> {
                    AccountGroup group = selectFamilyGroup();
                    if (group == null) break;

                    System.out.print("Amount: ");
                    group.deposit(scanner.nextDouble());
                    System.out.println("Deposit successful.");
                }

                case 9 -> {
                    AccountGroup group = selectFamilyGroup();
                    if (group == null) break;

                    System.out.print("Amount: ");
                    group.withdraw(scanner.nextDouble());
                    System.out.println("Withdraw successful.");
                }

                case 0 -> back = true;

                default -> System.out.println("Invalid option.");
            }
        }
    }





    static boolean handleChoice(int choice) {
        try {
            switch (choice) {

                case 1 -> {
                    AuthorizationService.checkPermission(currentUser, Permission.VIEW_OWN_ACCOUNT);
                    if (currentUser != null) {
                        System.out.println(currentUser);
                        if (currentAccount != null) {
                            System.out.println(currentAccount);


                            domain.service.RecommendationService.analyzeAndRecommend(currentAccount);
                        }
                    }
                    else System.out.println("No active account selected.");
                }


                case 2 -> {
                    if (currentUser.getRole() == Role.CUSTOMER) {
                        handleCustomerAccounts();
                    } else {
                        System.out.println("Option available for Customers only.");
                    }
                }


                case 3 -> {
                    AuthorizationService.checkPermission(currentUser, Permission.PROCESS_TRANSACTION);
                    System.out.print("Enter Deposit Amount: ");
                    currentAccount.deposit(scanner.nextDouble());
                }


                case 4 -> {
                    AuthorizationService.checkPermission(currentUser, Permission.PROCESS_TRANSACTION);
                    System.out.print("Enter Withdraw Amount: ");
                    currentAccount.withdraw(scanner.nextDouble());
                }


                case 7 -> {
                    AuthorizationService.checkPermission(currentUser, Permission.APPROVE_TRANSACTION);

                    Account target = currentAccount;
                    if (currentUser.getRole() == Role.ADMIN) {
                        System.out.print("Enter Account ID to modify: ");
                        String tId = scanner.next();
                        target = MockDatabase.getAccountById(tId);
                    }

                    if (target != null) {
                        System.out.println("Current: " + target.getStateName());
                        System.out.println("1. Freeze | 2. Suspend | 3. Activate | 4. Close");
                        int st = scanner.nextInt();
                        switch (st) {
                            case 1 -> target.freeze();
                            case 2 -> target.suspend();
                            case 3 -> target.activate();
                            case 4 -> target.close();
                        }
                    } else {
                        System.out.println("Account not found.");
                    }
                }


                case 8 -> {
                    AuthorizationService.checkPermission(currentUser, Permission.VIEW_REPORTS);
                    System.out.println("1. Daily | 2. Summary | 3. Audit | 4. Export");
                    int rep = scanner.nextInt();
                    switch (rep) {
                        case 1 -> ReportService.dailyTransactions();
                        case 2 -> ReportService.accountSummary();
                        case 3 -> ReportService.auditLogs();
                        case 4 -> ReportExporter.exportDailyTransactions("daily_report.txt");
                    }
                }


                case 5 -> {
                    AuthorizationService.checkPermission(currentUser, Permission.PROCESS_TRANSACTION);
                    System.out.print("Enter External Amount to Receive: ");
                    double val = scanner.nextDouble();
                    BankAdapter adapter = new BankAdapter(new ExternalBank());
                    adapter.pay(val);
                    currentAccount.deposit(val);
                }

                case 9 -> {
                    AuthorizationService.checkPermission(currentUser, Permission.MANAGE_USERS);

                    System.out.println("1. Overdraft ($1000) | 2. Insurance ($50k)");
                    int dec = scanner.nextInt();

                    System.out.print("Enter Account ID: ");
                    String accId = scanner.next();

                    Account target = MockDatabase.getAccountById(accId);

                    if (target == null) {
                        System.out.println("Account not found.");
                        return false;
                    }
                    if (dec == 1) {
                        target = new OverdraftProtectionDecorator(target, 1000);
                        System.out.println("Overdraft added to account " + accId);
                    } else {
                        target = new InsuranceDecorator(target, 50000, 50);
                        System.out.println("Insurance added to account " + accId);
                    }
                }

                case 10 -> {

                    AuthorizationService.checkPermission(currentUser, Permission.VIEW_SYSTEM_STATS);

                    System.out.print("Enter Account ID: ");
                    String accId = scanner.next();

                    Account target = MockDatabase.getAccountById(accId);

                    if (target == null) {
                        System.out.println("Account not found.");
                        return false;
                    }

                    target.applyInterest();
                    System.out.println("Interest applied. New Balance: " + target.getBalance());
                }

                case 6 -> {
                    // CUSTOMER
                    if (currentUser.getRole() == Role.CUSTOMER) {

                        System.out.println("\n--- Create Ticket ---");
                            System.out.print("Describe issue: ");
                            scanner.nextLine();
                            String issue = scanner.nextLine();
                            MockDatabase.addTicket(
                                    new domain.entities.SupportTicket(
                                            currentUser.getCustomerId(), issue
                                    )
                            );

                        return false;
                    }

                    // ADMIN / MANAGER
                    AuthorizationService.checkPermission(currentUser, Permission.MANAGE_USERS);
                    System.out.println(" View Tickets");
                    var tickets = MockDatabase.getAllTickets();
                    if (tickets.isEmpty()) {
                        System.out.println("No tickets.");
                    } else {
                        tickets.forEach(System.out::println);
                    }
                }


                case 0 -> { return true; }
                default -> System.out.println("Invalid option.");
            }
        } catch (SecurityException e) {
            System.out.println("⛔ ACCESS DENIED: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
        return false;
    }

    static boolean has(Permission p) {
        return AuthorizationService.hasPermission(currentUser, p);
    }

    static AccountGroup selectFamilyGroup() {
        if (currentUser.getFamilyGroups().isEmpty()) {
            System.out.println("No Family Groups available.");
            return null;
        }

        System.out.println("Select Family Group:");
        for (int i = 0; i < currentUser.getFamilyGroups().size(); i++) {
            System.out.println((i + 1) + ". " + currentUser.getFamilyGroups().get(i).getName());
        }

        int index = scanner.nextInt() - 1;
        scanner.nextLine();

        if (index < 0 || index >= currentUser.getFamilyGroups().size()) {
            System.out.println("Invalid selection.");
            return null;
        }

        return currentUser.getFamilyGroups().get(index);
    }

}
