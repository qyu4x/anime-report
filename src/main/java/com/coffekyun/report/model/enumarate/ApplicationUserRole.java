package com.coffekyun.report.model.enumarate;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.coffekyun.report.model.enumarate.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    ROLE_USER(Sets.newHashSet()),
    ROLE_ADMIN(Sets.newHashSet(USER_READ, USER_WRITE, ADMIN_READ, ADMIN_WRITE));

    private final Set<ApplicationUserPermission> permission;
    ApplicationUserRole(Set<ApplicationUserPermission> permission) {
        this.permission = permission;
    }

    public Set<ApplicationUserPermission> getPermission() {
        return permission;
    }
}
