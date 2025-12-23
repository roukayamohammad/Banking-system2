
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
import domain.observer.SMSObserver;
import domain.report.ReportExporter;
import domain.report.ReportService;
import domain.security.AuthorizationService;
import domain.security.Permission;
import domain.security.Role;

import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    static Customer currentUser;
    static Account currentAccount;
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

    // ================= REGISTER =================
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

    // ================= MENU =================
    static void showMenuByRole() {

        System.out.println("\n========= MENU =========");
        System.out.println("1. View Account Details");

        if (currentUser.getRole() == Role.CUSTOMER) {
            System.out.println("2. Manage My Accounts"); // جديد
        }

        if (has(Permission.PROCESS_TRANSACTION)) {
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
        }

        if (has(Permission.APPROVE_TRANSACTION)) {
            System.out.println("5. Change Account State");
        }

        if (has(Permission.VIEW_REPORTS)) {
            System.out.println("6. Reports");
        }

        System.out.println("0. Return to Login");
        System.out.print(">>> ");
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
        System.out.println("0. Back");
        System.out.print(">>> ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // تنظيف البافر

        switch (choice) {
            case 1 -> { // عرض الحسابات الفردية + الغروبات
                System.out.println("Your Accounts:");
                for (Account acc : currentUser.getAccounts()) {
                    System.out.println(acc);
                }

                if (!currentUser.getFamilyGroups().isEmpty()) {
                    System.out.println("Family Groups:");
                    for (AccountGroup group : currentUser.getFamilyGroups()) {
                        group.display(2); // تمرير 2 للـ indent
                    }
                }
            }

            case 2 -> { // إضافة حساب لغروب
                if (currentUser.getFamilyGroups().isEmpty()) {
                    System.out.println("No Family Group exists. Please create one first.");
                    break;
                }

                System.out.println("Select Family Group:");
                for (int i = 0; i < currentUser.getFamilyGroups().size(); i++) {
                    System.out.println((i + 1) + ". " + currentUser.getFamilyGroups().get(i).getName());
                }
                int gIndex = scanner.nextInt() - 1;
                scanner.nextLine(); // تنظيف البافر

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

            case 3 -> { // إزالة حساب من الغروب
                if (currentUser.getFamilyGroups().isEmpty()) {
                    System.out.println("No Family Group exists.");
                    break;
                }

                System.out.println("Select Family Group to remove account from:");
                for (int i = 0; i < currentUser.getFamilyGroups().size(); i++) {
                    System.out.println((i + 1) + ". " + currentUser.getFamilyGroups().get(i).getName());
                }
                int gIndex = scanner.nextInt() - 1;
                scanner.nextLine(); // تنظيف البافر

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

            case 4 -> { // إنشاء حساب جديد
                createAccountForExistingUser(currentUser);
            }

            case 5 -> { // إنشاء Family Group جديد
                System.out.print("Enter Family Group Name: ");
                String groupName = scanner.nextLine();

                AccountGroup newGroup = new AccountGroup(groupName);
                currentUser.addFamilyGroup(newGroup);

                System.out.println("Family Group [" + groupName + "] created successfully.");
            }

            case 0 -> back = true;

            default -> System.out.println("Invalid option");
        }
    }
}



    // ================= HANDLER =================
    static boolean handleChoice(int choice) {

        try {
            switch (choice) {

                case 1 -> {
                    AuthorizationService.checkPermission(
                            currentUser, Permission.VIEW_OWN_ACCOUNT);

                    if (currentAccount == null) {
                        System.out.println("Admin does not own a bank account.");
                        return false;
                    }

                    System.out.println(currentAccount);
                }

                case 2 -> {
                    if (currentUser.getRole() == Role.CUSTOMER) {
                        handleCustomerAccounts();
                    } else {
                        AuthorizationService.checkPermission(currentUser, Permission.PROCESS_TRANSACTION);
                        System.out.print("Amount: ");
                        currentAccount.deposit(scanner.nextDouble());
                    }
                }


                case 3 -> {
                    AuthorizationService.checkPermission(
                            currentUser, Permission.PROCESS_TRANSACTION);
                    System.out.print("Amount: ");
                    currentAccount.withdraw(scanner.nextDouble());
                }

                case 4 -> {
                    AuthorizationService.checkPermission(
                            currentUser, Permission.APPROVE_TRANSACTION
                    );

                    Account targetAccount = currentAccount;

                    // إذا المستخدم ADMIN، يحدد ID الحساب المراد تغييره
                    if (currentUser.getRole() == Role.ADMIN) {
                        System.out.print("Enter Account ID to change state: ");
                        String accId = scanner.next();
                        targetAccount = MockDatabase.getAccountById(accId); // تحتاج هذه الوظيفة في MockDatabase
                        if (targetAccount == null) {
                            System.out.println("Account not found!");
                            break;
                        }
                        System.out.println("Changing state for Account ID: " + accId);
                    }

                    System.out.println("\n--- Account State Management ---");
                    System.out.println("Current State: " + targetAccount.getStateName());
                    System.out.println("1. Freeze Account");
                    System.out.println("2. Suspend Account");
                    System.out.println("3. Activate Account");
                    System.out.println("4. Close Account");
                    System.out.print(">>> Choose action: ");
                    int stateChoice = scanner.nextInt();
                    switch (stateChoice) {
                        case 1 -> targetAccount.freeze();
                        case 2 -> targetAccount.suspend();
                        case 3 -> targetAccount.activate();
                        case 4 -> targetAccount.close();
                        default -> System.out.println("Invalid state option.");
                    }
                }


                case 5 -> {
                    AuthorizationService.checkPermission(
                            currentUser, Permission.VIEW_REPORTS
                    );

                    System.out.println("\n--- REPORT GENERATION ---");
                    System.out.println("1. Daily Transactions");
                    System.out.println("2. Account Summary");
                    System.out.println("3. Audit Log");
                    System.out.println("4. Export Daily Transactions");
                    System.out.print(">>> Select Report Type: ");

                    int reportChoice = scanner.nextInt();

                    switch (reportChoice) {
                        case 1 -> ReportService.dailyTransactions();
                        case 2 -> ReportService.accountSummary();
                        case 3 -> ReportService.auditLogs();
                        case 4 -> ReportExporter.exportDailyTransactions("daily_transactions.txt");
                        default -> System.out.println("Invalid report choice.");
                    }
                }

                case 6 -> {
                    AuthorizationService.checkPermission(
                            currentUser, Permission.MANAGE_USERS
                    );

                    System.out.println("1. Overdraft | 2. Insurance");
                    int d = scanner.nextInt();

                    if (d == 1)
                        currentAccount = new OverdraftProtectionDecorator(currentAccount, 1000);
                    else
                        currentAccount = new InsuranceDecorator(currentAccount, 50000, 50);
                }

                case 7 -> {
                    AuthorizationService.checkPermission(
                            currentUser, Permission.VIEW_SYSTEM_STATS);
                    currentAccount.applyInterest();
                    System.out.println("New Balance: $" + currentAccount.getBalance());
                }

                case 0 -> {
                    System.out.println("\nReturning to login screen...\n");
                    return true; // إعادة المستخدم لشاشة البداية
                }

                default -> System.out.println("Invalid Option");
            }

        } catch (SecurityException e) {
            System.out.println("ACCESS DENIED");
        }

        return false;
    }

    static boolean has(Permission p) {
        return AuthorizationService.hasPermission(currentUser, p);
    }
}
