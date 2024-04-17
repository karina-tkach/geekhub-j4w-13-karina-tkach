package org.geekhub.ticketbooking.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER("USER"),
    ADMIN("ADMIN"),
    SUPER_ADMIN("SUPER_ADMIN");
    private final String name;

    public String getName() {
        return name;
    }

    Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
