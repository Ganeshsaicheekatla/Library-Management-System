package com.example.Book.Management.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_STUDENT,
    ROLE_LIBRARIAN,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
