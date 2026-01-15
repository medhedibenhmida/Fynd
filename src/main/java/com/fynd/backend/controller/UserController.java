package com.fynd.backend.controller;

import com.fynd.backend.dto.AuthUser;
import com.fynd.backend.dto.UserResponse;
import com.fynd.backend.entities.User;
import com.fynd.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser (@RequestBody User user){
        try {
            User created = userService.create(user);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<User> getUser (@PathVariable String uuid){
        return userService.getByUuid(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateUser (@PathVariable String uuid, @RequestBody User user){
        try{
            User updatedUser = userService.updateUser(uuid,user);
            return ResponseEntity.ok(updatedUser);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteUser(@PathVariable String uuid) {
        try {
            User deletedUser = userService.deleteUser(uuid);
            return ResponseEntity.ok(deletedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/me")
    public UserResponse getCurrentUser(Authentication authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        User user = userService.getByUuid(authUser.getUuid())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return UserResponse.builder()
                .uuid(user.getUuid())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .age(user.getAge())
                .role(user.getRole())
                .userStatus((user.getUserStatus()))
                .build();
    }

}
