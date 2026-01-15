package com.fynd.backend.dto;

import lombok.Getter;

@Getter
public class AuthUser {
    private final String uuid;
    private final String email;

    public AuthUser(String uuid, String email) {
        this.uuid = uuid;
        this.email = email;
    }
}
