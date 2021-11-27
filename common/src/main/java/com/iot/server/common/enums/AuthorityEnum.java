package com.iot.server.common.enums;

public enum AuthorityEnum {
    ADMIN("ADMIN"),
    USER("USER");

    private final String permission;

    AuthorityEnum(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public static AuthorityEnum getAuthority(String value) {
        AuthorityEnum authorityEnum = null;
        if (value != null && value.length() != 0) {
            for (AuthorityEnum current : AuthorityEnum.values()) {
                if (current.name().equalsIgnoreCase(value)) {
                    authorityEnum = current;
                    break;
                }
            }
        }
        return authorityEnum;
    }
}
