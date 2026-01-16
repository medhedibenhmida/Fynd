package com.fynd.backend.service;

import com.fynd.backend.entities.User;
import com.fynd.backend.enums.UserStatus;
import com.fynd.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create (User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent() || userRepository.findByPhone(user.getPhone()).isPresent())
        {
            throw new RuntimeException("Email ou téléphone déjà utilisé");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    public User findByEmail(String email) {

        return userRepository.findByEmail(email).
                orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec email: " + email));
    }

    public String saveProfilePicture(User user, MultipartFile file) throws IOException {

        String uploadDir = System.getProperty("user.dir") + "/uploads/avatars/";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = user.getUuid() + "_" + Objects.requireNonNull(file.getOriginalFilename()).replaceAll("\\s+", "_");
        Path filePath = uploadPath.resolve(fileName);

        file.transferTo(filePath.toFile());

        String url = "/uploads/avatars/" + fileName; // accessible par Angular
        user.setProfilePicture(url);
        userRepository.save(user);

        return url;

    }
}
