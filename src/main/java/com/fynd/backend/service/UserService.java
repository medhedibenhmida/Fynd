package com.fynd.backend.service;

import com.fynd.backend.entities.User;
import com.fynd.backend.enums.UserStatus;
import com.fynd.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create (User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent() || userRepository.findByPhone(user.getPhone()).isPresent())
        {
            throw new RuntimeException("Email ou téléphone déjà utilisé");
        }
      return userRepository.save(user);
    }

    public Optional<User> getByUuid(String uuid) {
        return userRepository.findByUuid(uuid);
    }

    public User updateUser(String uuid, User updatedUser){
        Optional<User> optionalUser = userRepository.findByUuid(uuid);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Utilisateur non trouvé");
        }

        User user = optionalUser.get();

        if (updatedUser.getFirstName() != null) user.setFirstName(updatedUser.getFirstName());
        if (updatedUser.getLastName() != null) user.setLastName(updatedUser.getLastName());
        if (updatedUser.getPhone() != null) user.setPhone(updatedUser.getPhone());
        if (updatedUser.getAge() != 0) user.setAge(updatedUser.getAge());
        if (updatedUser.getRole() != null) user.setRole(updatedUser.getRole());
        if (updatedUser.getUserStatus() != null) user.setUserStatus(updatedUser.getUserStatus());

        updatedUser.setUpdated_at(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User deleteUser (String uuid){
        Optional<User> optionalUser = userRepository.findByUuid(uuid);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Utilisateur non trouvé");
        }
        User user = optionalUser.get();
        user.setUserStatus(UserStatus.DELETED);
        return userRepository.save(user);
    }
}
