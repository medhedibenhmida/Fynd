package com.fynd.backend.entities;

import com.fynd.backend.enums.MembersStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Activity activity;

    @Enumerated(EnumType.STRING)
    private MembersStatus membersStatus;

    private LocalDateTime requestedAt;
    private LocalDateTime decisionDate;
}
