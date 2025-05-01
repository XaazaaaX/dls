package de.dlsa.api.controllers;

import de.dlsa.api.dtos.UserDto;
import de.dlsa.api.entities.Settings;
import de.dlsa.api.entities.User;
import de.dlsa.api.responses.UserResponse;
import de.dlsa.api.services.SettingsService;
import de.dlsa.api.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/settings")
@RestController
public class SettingsController {

    private final SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    //GET /api/users
    @GetMapping
    public ResponseEntity<Settings> getSettings() {
        return ResponseEntity.ok(settingsService.getSettings());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Settings> updateUser(@PathVariable long id, @RequestBody Settings settings) {
        Settings updated = settingsService.updateSettings(id, settings);
        return ResponseEntity.ok(updated);
    }
}
