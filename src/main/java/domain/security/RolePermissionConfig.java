package domain.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RolePermissionConfig {

    private static final Map<Role, Set<Permission>> rolePermissions = new HashMap<>();

    static {


        rolePermissions.put(Role.CUSTOMER, Set.of(
                Permission.VIEW_OWN_ACCOUNT,
                Permission.PROCESS_TRANSACTION
        ));


        rolePermissions.put(Role.TELLER, Set.of(
                Permission.VIEW_OWN_ACCOUNT,
                Permission.PROCESS_TRANSACTION
        ));


        rolePermissions.put(Role.MANAGER, Set.of(
                Permission.VIEW_OWN_ACCOUNT,
             //   Permission.PROCESS_TRANSACTION,
                Permission.APPROVE_TRANSACTION,
                Permission.VIEW_REPORTS,
                Permission.VIEW_SYSTEM_STATS
        ));


        rolePermissions.put(Role.ADMIN, Set.of(
                Permission.VIEW_OWN_ACCOUNT,

                Permission.APPROVE_TRANSACTION,
                Permission.VIEW_REPORTS,
                Permission.VIEW_SYSTEM_STATS,
                Permission.MANAGE_USERS
        ));
    }

    public static boolean hasPermission(Role role, Permission permission) {
        return rolePermissions
                .getOrDefault(role, Set.of())
                .contains(permission);
    }
}
