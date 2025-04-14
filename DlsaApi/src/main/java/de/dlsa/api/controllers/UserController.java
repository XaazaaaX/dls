package de.dlsa.api.controllers;

import de.dlsa.api.dtos.UserDto;
import de.dlsa.api.entities.User;
import de.dlsa.api.responses.UserResponse;
import de.dlsa.api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // POST /api/users
    @PostMapping("/user")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserDto user) {
        UserResponse created = userService.createUser(user);
        return ResponseEntity.ok(created);
    }


    // PUT /api/users/{id}
    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable long id, @RequestBody UserDto userDetails) {
        UserResponse updated = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/users/{id}
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}