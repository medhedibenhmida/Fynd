package com.fynd.backend.entities;

import com.fynd.backend.enums.ActivityApprovalStatus;
import com.fynd.backend.enums.ActivityLifecycleState;
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
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime created_at;
    private LocalDateTime  updated_at;
    private LocalDateTime  plannedDate;
    private String title;
    private String location;
    private String type;
    private String notes;

    @ManyToOne
    private User creator;

    @OneToMany(mappedBy = "activity")
    private List<Members> members;

    private boolean isPrivate;
    private int maxParticipants;

    @Enumerated(EnumType.STRING)
    private ActivityApprovalStatus activityApprovalStatus ;

    @Enumerated(EnumType.STRING)
    private ActivityLifecycleState activityLifecycleState;
}
