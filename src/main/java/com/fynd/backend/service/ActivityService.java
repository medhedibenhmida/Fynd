package com.fynd.backend.service;

import com.fynd.backend.dto.ActivityRequest;
import com.fynd.backend.dto.ActivityResponse;
import com.fynd.backend.dto.UserResponse;
import com.fynd.backend.entities.Activity;
import com.fynd.backend.entities.User;
import com.fynd.backend.enums.ActivityApprovalStatus;
import com.fynd.backend.enums.ActivityLifecycleState;
import com.fynd.backend.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserService userService;


    public ActivityService(ActivityRepository activityRepository, UserService userService) {
        this.activityRepository = activityRepository;
        this.userService = userService;
    }

    public ActivityResponse createActivity (ActivityRequest request, String userUuid){

        User activityCreator = userService.getByUuid(userUuid).orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Activity activity = new Activity();

        activity.setTitle(request.getTitle());
        activity.setDescription(request.getDescription());
        activity.setCreated_at(LocalDateTime.now());
        activity.setPlannedDate(request.getPlannedDate());
        activity.setTitle(request.getTitle());
        activity.setPrivate(request.isPrivate());
        activity.setGenderPreference(request.getGenderPreference());
        activity.setMaxParticipants(request.getMaxParticipants());
        activity.setActivityApprovalStatus(ActivityApprovalStatus.PENDING);
        activity.setActivityLifecycleState(ActivityLifecycleState.UPCOMING);
        activity.setCreator(activityCreator);

        Activity saved = activityRepository.save(activity);

        UserResponse userResponse = new UserResponse(activityCreator.getUuid(), activityCreator.getEmail());

        return  new ActivityResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getLocation(),
                saved.getType(),
                saved.isPrivate(),
                saved.getMaxParticipants(),
                saved.getGenderPreference(),
                saved.getPlannedDate(),
                userResponse
        );
    }
}
