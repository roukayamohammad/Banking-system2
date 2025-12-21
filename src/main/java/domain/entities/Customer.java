package domain.entities;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String customerId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private List<Account> accounts;

    public Customer(String customerId, String name, String email, String phone, String address) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
        System.out.println("Account " + account.getAccountId() + " added to customer " + name);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
    }

    public double getTotalBalance() {
        double total = 0;
        for (Account account : accounts) {
            total += account.getBalance();
        }
        return total;
    }

    // Getters
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public List<Account> getAccounts() { return new ArrayList<>(accounts); }

    @Override
    public String toString() {
        return String.format("Customer{id='%s', name='%s', email='%s', accounts=%d}",
                customerId, name, email, accounts.size());
    }
}
