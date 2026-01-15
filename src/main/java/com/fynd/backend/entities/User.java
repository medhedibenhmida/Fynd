package com.fynd.backend.entities;

import com.fynd.backend.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, updatable = false, length = 36)
    private String uuid;

    private String firstName;
    private String lastName;
    private long age;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String phone;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private String selfieUrl;
    private String documentUrl;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "creator")
    private List<Activity> activitiesCreated;

    @OneToMany(mappedBy = "user")
    private List<Members> members;

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = java.util.UUID.randomUUID().toString();
        }
        if (userStatus == null) {
            userStatus = UserStatus.NOT_VERIFIED;
        }
        if (role == null) {
            role = Role.USER;
        }
        if (created_at == null) {
            created_at = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        updated_at = LocalDateTime.now();
    }
}
