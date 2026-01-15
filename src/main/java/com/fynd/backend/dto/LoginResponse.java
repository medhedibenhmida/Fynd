package com.fynd.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private String uuid;
    private String role;
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }
}
