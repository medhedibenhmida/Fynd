package com.fynd.backend.repository;

import com.fynd.backend.entities.Activity;
import com.fynd.backend.enums.ActivityLifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    // Récupère les activités d'un utilisateur avec des états spécifiques
    List<Activity> findByCreatorUuidAndActivityLifecycleStateIn(
            String uuid, List<ActivityLifecycleState> states);
}
