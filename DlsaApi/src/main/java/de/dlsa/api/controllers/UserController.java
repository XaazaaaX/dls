package de.dlsa.api.controllers;

import de.dlsa.api.dtos.UserDto;
import de.dlsa.api.entities.User;
import de.dlsa.api.responses.UserResponse;
import de.dlsa.api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PreAuthorize("hasAuthority('Administrator')")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // POST /api/users
    @PreAuthorize("hasAuthority('Administrator')")
    @PostMapping("/user")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserDto user) {
        UserResponse created = userService.createUser(user);
        return ResponseEntity.ok(created);
    }


    // PUT /api/users/{id}
    @PreAuthorize("hasAuthority('Administrator')")
    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable long id, @RequestBody UserDto userDetails) {
        UserResponse updated = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/users/{id}
    @PreAuthorize("hasAuthority('Administrator')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}