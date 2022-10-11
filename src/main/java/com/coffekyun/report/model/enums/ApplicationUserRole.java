package com.coffekyun.report.model.enums;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.coffekyun.report.model.enums.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    USER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(USER_READ, USER_WRITE, ADMIN_READ, ADMIN_WRITE)),

    ADMIN_TRAINEE(Sets.newHashSet(USER_READ, ADMIN_READ));
    private final Set<ApplicationUserPermission> permission;
    ApplicationUserRole(Set<ApplicationUserPermission> permission) {
        this.permission = permission;
    }

    public Set<ApplicationUserPermission> getPermission() {
        return permission;
    }
}
