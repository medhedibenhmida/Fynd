package com.fynd.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ActivityResponse {
    private Long id;
    private String title;
    private String description;
    private String location;
    private String type;
    private boolean isPrivate;
    private int maxParticipants;
    private String genderPreference;
    private LocalDateTime plannedDate;
    private UserResponse creator;
}
