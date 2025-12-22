/*package domain.entities;

import java.util.HashMap;
import java.util.Map;

public class MockDatabase {

    // محاكاة لجدول العملاء في قاعدة البيانات
    private static final Map<String, Customer> customers = new HashMap<>();

    static {
        // ⚠️ هنا سنضيف بيانات وهمية، ولكن يجب أن تتطابق مع الـ Constructor الخاص بك:
        // (ID, Name, Email, Phone, Address)

        // العميل الأول (تجريبي)
        customers.put("1", new Customer("1", "Ahmad", "ahmad@example.com", "0911111111", "Damascus"));

        // العميل الثاني (مهم جداً للتجربة)
        // 🔴 ضع إيميلك الحقيقي هنا لكي تصلك الرسالة عند التجربة
        customers.put("101", new Customer("101", "My Test User", "tukaalshallah2000@gmail.com", "0922222222", "Aleppo"));

        // عميل ثالث
        customers.put("2", new Customer("2", "Sara", "sara@test.com", "0933333333", "Homs"));
    }

    // دالة البحث عن العميل
    public static Customer getCustomerById(String customerId) {
        // نقوم بتحويل الـ ID إلى String لضمان التوافق (في حال كان المرسل رقم int)
        return customers.get(String.valueOf(customerId));
    }

    // دالة إضافية احتياطية في حال كان الـ ID يأتي كرقم (int) من كلاس Account
    public static Customer getCustomerById(int customerId) {
        return customers.get(String.valueOf(customerId));
    }
}*/

package domain.entities;


import java.util.ArrayList;

import domain.security.Role;

import java.util.Collection;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class MockDatabase {

    // 🔹 تخزين كل الزبائن
    private static final Map<String, Customer> customers = new HashMap<>();

    // 🔹 تخزين كل الحسابات في النظام (مهم للـ Composite)
    private static final List<Account> allAccounts = new ArrayList<>();


    static {
        customers.put("1", new Customer("1", "touka", "touka@example.com", "0911111111", "Damascus",Role.ADMIN));

        customers.put("1", new Customer("1", "touka", "touka@example.com", "0911111111", "Damascus", Role.CUSTOMER));

    }

    // ---------------------- Customers ----------------------

    public static void addCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
        System.out.println(" Database Log: Customer [" + customer.getName() + "] saved successfully.");
    }

    public static boolean isCustomerExist(String id) {
        return customers.containsKey(id);
    }

    public static Customer getCustomerById(String customerId) {
        return customers.get(String.valueOf(customerId));
    }



    // ---------------------- Accounts ----------------------

    // 🔹 إضافة حساب جديد إلى القائمة العامة
    public static void addAccount(Account acc) {
        allAccounts.add(acc);
    }

    // 🔹 إرجاع كل الحسابات في النظام
    public static List<Account> getAllAccounts() {
        return allAccounts;
    }
       public static Collection<Customer> getAllCustomers() {
        return customers.values();
    }
    private static final java.util.List<SupportTicket> tickets = new java.util.ArrayList<>();

    public static void addTicket(SupportTicket ticket) {
        tickets.add(ticket);
        System.out.println(" Support Ticket Created Successfully!");
    }

    public static java.util.List<SupportTicket> getAllTickets() {
        return tickets;
    }
}

 

