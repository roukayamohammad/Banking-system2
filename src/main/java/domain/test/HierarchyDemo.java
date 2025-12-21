package domain.test;

import domain.composite.AccountGroup;
import domain.composite.SingleAccount;
import domain.entities.Account;
import domain.factory.AccountFactory;

/**
 * عرض توضيحي لمتطلب Account Hierarchy
 * باستخدام Composite Design Pattern
 */
public class HierarchyDemo {

    public static void main(String[] args) {
        demonstrateFamilyHierarchy();
        demonstrateCompanyHierarchy();
        demonstrateBankHierarchy();
    }

    /**
     * مثال 1: هيكل عائلي
     */
    private static void demonstrateFamilyHierarchy() {
        System.out.println("\n🎯 المثال 1: هيكل حسابات عائلي");
        System.out.println("================================\n");

        // إنشاء حسابات أفراد العائلة
        Account father = createAccount("الأب", "SAVINGS", 15000);
        Account mother = createAccount("الأم", "CHECKING", 8000);
        Account son = createAccount("الابن", "SAVINGS", 3000);
        Account daughter = createAccount("الابنة", "SAVINGS", 2000);

        // بناء الهيكل الهرمي
        AccountGroup family = new AccountGroup("العائلة");

        AccountGroup parents = new AccountGroup("الوالدان");
        parents.add(new SingleAccount(father));
        parents.add(new SingleAccount(mother));

        AccountGroup children = new AccountGroup("الأبناء");
        children.add(new SingleAccount(son));
        children.add(new SingleAccount(daughter));

        family.add(parents);
        family.add(children);

        // عرض النتائج
        System.out.println("الهيكل الهرمي للعائلة:");
        family.display(2);

        System.out.println("\nالإحصاءات:");
        System.out.println("- الرصيد الكلي: $" + family.getBalance());
        System.out.println("- رصيد الوالدين: $" + parents.getBalance());
        System.out.println("- رصيد الأبناء: $" + children.getBalance());
        System.out.println("- عدد المستويات: 3 (عائلة ← والدين/أبناء ← أفراد)");
    }

    /**
     * مثال 2: هيكل شركة
     */
    private static void demonstrateCompanyHierarchy() {
        System.out.println("\n🎯 المثال 2: هيكل حسابات شركة");
        System.out.println("================================\n");

        AccountGroup company = new AccountGroup("شركة الأمل للتجارة");

        // الفروع
        AccountGroup branch1 = new AccountGroup("الفرع الرئيسي");
        branch1.add(createSingleAccount("الحساب الجاري الرئيسي", "CHECKING", 50000));
        branch1.add(createSingleAccount("حساب الاستثمار", "INVESTMENT", 100000));

        AccountGroup branch2 = new AccountGroup("فرع الرياض");
        branch2.add(createSingleAccount("حساب المبيعات", "CHECKING", 30000));
        branch2.add(createSingleAccount("حساب المصروفات", "CHECKING", 10000));

        // الإدارات
        AccountGroup salesDept = new AccountGroup("إدارة المبيعات");
        salesDept.add(createSingleAccount("عمولات المبيعات", "SAVINGS", 15000));

        AccountGroup hrDept = new AccountGroup("إدارة الموارد البشرية");
        hrDept.add(createSingleAccount("رواتب الموظفين", "CHECKING", 40000));

        // بناء الهيكل
        branch1.add(salesDept);
        branch1.add(hrDept);

        company.add(branch1);
        company.add(branch2);

        // عرض النتائج
        System.out.println("الهيكل التنظيمي للشركة:");
        company.display(2);

        System.out.println("\nالتقرير المالي:");
        System.out.println("- إجمالي أصول الشركة: $" + company.getBalance());
        System.out.println("- أصول الفرع الرئيسي: $" + branch1.getBalance());
        System.out.println("- أوامل فرع الرياض: $" + branch2.getBalance());

        // عملية على مستوى الشركة
        System.out.println("\nعملية على مستوى الشركة (إيداع 10000 لكل الحسابات):");
        company.deposit(10000);
        System.out.println("الأصول الجديدة: $" + company.getBalance());
    }

    /**
     * مثال 3: هيكل بنكي (متقدم)
     */
    private static void demonstrateBankHierarchy() {
        System.out.println("\n🎯 المثال 3: هيكل حسابات بنكي متداخل");
        System.out.println("========================================\n");

        // إنشاء هيكل متداخل
        AccountGroup bank = new AccountGroup("البنك الوطني");

        for (int i = 1; i <= 3; i++) {
            AccountGroup cityGroup = new AccountGroup("مدينة " + i);

            for (int j = 1; j <= 2; j++) {
                AccountGroup branch = new AccountGroup("فرع " + j);

                for (int k = 1; k <= 3; k++) {
                    AccountGroup customer = new AccountGroup("عميل " + k);

                    customer.add(createSingleAccount("توفير", "SAVINGS", 1000 * k));
                    customer.add(createSingleAccount("جاري", "CHECKING", 500 * k));

                    branch.add(customer);
                }

                cityGroup.add(branch);
            }

            bank.add(cityGroup);
        }

        System.out.println("الهيكل المتداخل (مبسط):");
        System.out.println("- البنك الوطني");
        System.out.println("  ├── مدينة 1");
        System.out.println("  │   ├── فرع 1");
        System.out.println("  │   │   ├── عميل 1");
        System.out.println("  │   │   ├── عميل 2");
        System.out.println("  │   │   └── عميل 3");
        System.out.println("  │   └── فرع 2");
        System.out.println("  ├── مدينة 2");
        System.out.println("  └── مدينة 3");

        System.out.println("\nإجمالي الأصول عبر جميع المستويات: $" + bank.getBalance());

        // إظهار مرونة الهيكل
        System.out.println("\n✨ إثبات المرونة:");

        // حساب متوسط رصيد العميل
        double totalBalance = bank.getBalance();
        int estimatedCustomers = 3 * 2 * 3; // 3 مدن × 2 فروع × 3 عملاء
        double avgPerCustomer = totalBalance / estimatedCustomers;

        System.out.println("- متوسط رصيد العميل: $" + String.format("%.2f", avgPerCustomer));
        System.out.println("- يمكن إضافة/إزالة أي مستوى دون التأثير على الباقي");
        System.out.println("- العمليات (إيداع/سحب) تعمل على أي مستوى");
    }

    // ===== دوال مساعدة =====

    private static Account createAccount(String owner, String type, double balance) {
        String id = "ACC_" + owner.replace(" ", "") + "_" + System.currentTimeMillis();
        return AccountFactory.createAccount(type, id, owner, balance);
    }

    private static SingleAccount createSingleAccount(String name, String type, double balance) {
        Account account = createAccount(name, type, balance);
        return new SingleAccount(account);
    }
}