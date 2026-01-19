package com.fynd.backend.dto;

import com.fynd.backend.enums.Role;
import com.fynd.backend.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    public UserResponse(String uuid, String email) {
        this.uuid=uuid;
        this.email=email;
    }
}