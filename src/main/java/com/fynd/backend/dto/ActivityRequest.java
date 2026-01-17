package com.fynd.backend.dto;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityRequest {
    @NotBlank(message = "Le titre est obligatoire")
    private String title;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    private String location;
    private String type;

    private boolean isPrivate;

    @Min(value = 1, message = "Minimum 1 participant")
    @Max(value = 100, message = "Maximum 100 participants")
    private int maxParticipants;

    private String genderPreference;

    @Future(message = "La date prévue doit être dans le futur")
    private LocalDateTime plannedDate;
}
