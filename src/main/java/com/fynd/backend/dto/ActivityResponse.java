package com.fynd.backend.dto;

import com.fynd.backend.enums.ActivityApprovalStatus;
import com.fynd.backend.enums.ActivityLifecycleState;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ActivityResponse {
    private Long id;
    private String title;
    private String location;
    private String type;
    private boolean isPrivate;
    private int maxParticipants;
    private LocalDateTime plannedDate;
    private UserResponse creator;
    private ActivityLifecycleState activityLifecycleState;
    private ActivityApprovalStatus activityApprovalStatus;

}
