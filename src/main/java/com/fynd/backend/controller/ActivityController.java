package com.fynd.backend.controller;

import com.fynd.backend.dto.ActivityRequest;
import com.fynd.backend.dto.ActivityResponse;
import com.fynd.backend.dto.AuthUser;
import com.fynd.backend.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }
    @PostMapping
    public ActivityResponse createActivity(@Valid @RequestBody ActivityRequest request,
                                           @AuthenticationPrincipal AuthUser authUser) {
        return activityService.createActivity(request, authUser.getUuid());
    }
}
