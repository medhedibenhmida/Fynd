package com.fynd.backend.entities;

import com.fynd.backend.enums.ActivityStatus;
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
    private String description;
    private LocalDateTime created_at;
    private LocalDateTime  updated_at;
    private String title;
    private String location;
    private String type;

    @ManyToOne
    private User creator;

    @OneToMany(mappedBy = "activity")
    private List<Members> members;

    private boolean isPrivate;
    private int maxParticipants;
    private String genderPreference;

    @Enumerated(EnumType.STRING)
    private ActivityStatus activityStatus;
}
