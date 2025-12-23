
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
   static AccountGroup familyGroup = null;
//    static AccountGroup familyGroup = new AccountGroup("My Family Group");
//static AccountGroup familyGroup = null; // ما في غروب افتراضي


    static NotificationService notificationService = new NotificationService();

    static RealEmailObserver emailService = new RealEmailObserver(
            "tukaalshallah2000@gmail.com",
            "lksu blhe kdqs wqhd"
    );

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("      DAMASCUS BANK SYSTEM        ");
        System.out.println("=====================================");

        while (true) {
            loginOrRegister(); // شاشة البداية
            currentAccount = currentUser.getAccounts().isEmpty() ? null : currentUser.getAccounts().get(0);

            boolean backToLogin = false;

            while (!backToLogin) {
                showMenuByRole();
                int choice = scanner.nextInt();
                backToLogin = handleChoice(choice); // true لو ضغط 0
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
        System.out.println("\n========= MENU =========");
        System.out.println("1. View Account Details");


        if (currentUser.getRole() == Role.CUSTOMER) {
            System.out.println("2. Manage My Accounts (Composite)");
            System.out.println("12. Customer Support");
        }


        if (has(Permission.PROCESS_TRANSACTION)) {
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("8. External Transfer (Adapter)");
        }


        if (has(Permission.APPROVE_TRANSACTION)) {
            System.out.println("5. Change Account State");
            System.out.println("12. Customer Support (View Tickets)");
        }


        if (has(Permission.VIEW_REPORTS)) {
            System.out.println("6. Reports");
        }


        if (has(Permission.MANAGE_USERS)) {
            System.out.println("9. Add Features (Decorator: Insurance/Overdraft)");
        }

        if (has(Permission.VIEW_SYSTEM_STATS)) {
            System.out.println("10. Apply Interest (Strategy)");
        }

        System.out.println("0. Return to Login");
        System.out.print(">>> Please enter number: ");
    }
    //
    static void handleCustomerAccounts() {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- Manage My Accounts ---");
            System.out.println("1. View My Accounts");
            System.out.println("2. Add Account to Family Group");
            System.out.println("3. Remove Account from Family Group");
            System.out.println("4. Create New Account");
            System.out.println("5. Create New Family Group");
            System.out.println("6.   Manage Family Group (Composite)");
            System.out.println("0. Back");
            System.out.print(">>> ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("Your Accounts:");
                    for (Account acc : currentUser.getAccounts()) {
                        System.out.println(acc);
                    }

                    if (!currentUser.getFamilyGroups().isEmpty()) {
                        System.out.println("Family Groups:");
                        for (AccountGroup group : currentUser.getFamilyGroups()) {
                            group.display(2);
                        }
                    }
                }

                case 2 -> {
                    if (currentUser.getFamilyGroups().isEmpty()) {
                        System.out.println("No Family Group exists. Please create one first.");
                        break;
                    }

                    System.out.println("Select Family Group:");
                    for (int i = 0; i < currentUser.getFamilyGroups().size(); i++) {
                        System.out.println((i + 1) + ". " + currentUser.getFamilyGroups().get(i).getName());
                    }
                    int gIndex = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (gIndex < 0 || gIndex >= currentUser.getFamilyGroups().size()) {
                        System.out.println("Invalid group selection.");
                        break;
                    }

                    AccountGroup selectedGroup = currentUser.getFamilyGroups().get(gIndex);

                    System.out.print("Enter Account ID to add to family group: ");
                    String accId = scanner.nextLine();

                    Account accToAdd = currentUser.getAccounts().stream()
                            .filter(a -> a.getAccountId().equals(accId))
                            .findFirst()
                            .orElse(null);

                    if (accToAdd == null) {
                        System.out.println("Account not found!");
                        break;
                    }

                    selectedGroup.add(new SingleAccount(accToAdd));
                    System.out.println("Account added to family group [" + selectedGroup.getName() + "].");
                }

                case 3 -> {
                    if (currentUser.getFamilyGroups().isEmpty()) {
                        System.out.println("No Family Group exists.");
                        break;
                    }

                    System.out.println("Select Family Group to remove account from:");
                    for (int i = 0; i < currentUser.getFamilyGroups().size(); i++) {
                        System.out.println((i + 1) + ". " + currentUser.getFamilyGroups().get(i).getName());
                    }
                    int gIndex = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (gIndex < 0 || gIndex >= currentUser.getFamilyGroups().size()) {
                        System.out.println("Invalid group selection.");
                        break;
                    }

                    AccountGroup selectedGroup = currentUser.getFamilyGroups().get(gIndex);

                    System.out.print("Enter Account ID to remove from family group: ");
                    String accId = scanner.nextLine();

                    Account accToRemove = currentUser.getAccounts().stream()
                            .filter(a -> a.getAccountId().equals(accId))
                            .findFirst()
                            .orElse(null);

                    if (accToRemove != null) {
                        selectedGroup.remove(new SingleAccount(accToRemove));
                        System.out.println("Account removed from family group [" + selectedGroup.getName() + "].");
                    } else {
                        System.out.println("Account not found!");
                    }
                }

                case 4 -> {
                    createAccountForExistingUser(currentUser);
                }

                case 5 -> {
                    System.out.print("Enter Family Group Name: ");
                    String groupName = scanner.nextLine();

                    AccountGroup newGroup = new AccountGroup(groupName);
                    currentUser.addFamilyGroup(newGroup);

                    System.out.println("Family Group [" + groupName + "] created successfully.");
                }


                case 6 -> {
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
                       // AccountGroup familyGroup = null;
                        case 1 -> {
                            System.out.print("Group name: ");
                            familyGroup = new AccountGroup(scanner.next());
                        }
                        case 2 -> {
                            if (familyGroup == null) break;
                            familyGroup.add(new SingleAccount(currentAccount));
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


                case 0 -> back = true;

                default -> System.out.println("Invalid option");
            }
        }
    }




    static boolean handleChoice(int choice) {
        try {
            switch (choice) {

                case 1 -> {
                    AuthorizationService.checkPermission(currentUser, Permission.VIEW_OWN_ACCOUNT);
                    if (currentAccount != null) System.out.println(currentAccount);
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


                case 5 -> {
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


                case 6 -> {
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


                case 8 -> {
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
                    if (dec == 1) {
                        currentAccount = new OverdraftProtectionDecorator(currentAccount, 1000);
                        System.out.println("Overdraft Added!");
                    } else {
                        currentAccount = new InsuranceDecorator(currentAccount, 50000, 50);
                        System.out.println("Insurance Added!");
                    }
                }


                case 10 -> {
                    AuthorizationService.checkPermission(currentUser, Permission.VIEW_SYSTEM_STATS);
                    currentAccount.applyInterest();
                    System.out.println("Interest Applied. New Balance: " + currentAccount.getBalance());
                }


                case 12 -> {
                    System.out.println("\n--- Customer Support ---");
                    System.out.println("1. Create Ticket (Customer) | 2. View Tickets (Admin)");
                    int sup = scanner.nextInt();
                    if (sup == 1) {
                        System.out.print("Describe issue: ");
                        scanner.nextLine();
                        String issue = scanner.nextLine();
                        MockDatabase.addTicket(new domain.entities.SupportTicket(currentUser.getCustomerId(), issue));
                    } else {
                        AuthorizationService.checkPermission(currentUser, Permission.MANAGE_USERS);
                        var tickets = MockDatabase.getAllTickets();
                        if (tickets.isEmpty()) System.out.println("No tickets.");
                        else tickets.forEach(System.out::println);
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
}
