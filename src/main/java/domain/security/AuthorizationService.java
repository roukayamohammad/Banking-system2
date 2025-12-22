package domain.security;

import domain.entities.Customer;

public class AuthorizationService {

    public static void checkPermission(Customer user, Permission permission) {
        if (!RolePermissionConfig.hasPermission(user.getRole(), permission)) {
            throw new SecurityException(
                    "Access denied for user: " + user.getName()
            );
        }
    }
}
