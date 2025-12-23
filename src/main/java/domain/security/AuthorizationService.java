package domain.security;

import domain.entities.Customer;

public class AuthorizationService {

    // تستخدم داخل العمليات (Deposit, Withdraw...)
    public static void checkPermission(Customer user, Permission permission) {

        if (user == null) {
            throw new SecurityException("User not logged in");
        }

        if (!RolePermissionConfig.hasPermission(user.getRole(), permission)) {
            throw new SecurityException(
                    "Access denied for role: " + user.getRole()
            );
        }
    }

    // تستخدم لعرض المينيو حسب الدور
    public static boolean hasPermission(Customer user, Permission permission) {

        if (user == null) return false;

        return RolePermissionConfig.hasPermission(
                user.getRole(), permission
        );
    }
}
