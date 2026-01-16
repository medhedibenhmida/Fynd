package com.fynd.backend.dto;

import com.fynd.backend.enums.Role;
import com.fynd.backend.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private long age;
    private Role role;
    private UserStatus userStatus;
    private String profilePicture;
}