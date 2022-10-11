package com.coffekyun.report.model.enums;

public enum ApplicationUserPermission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write");

    private final String description;
    ApplicationUserPermission(String description) {
        this.description = description;
    }
}
