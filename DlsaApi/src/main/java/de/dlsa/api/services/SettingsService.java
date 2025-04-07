package de.dlsa.api.services;

import de.dlsa.api.dtos.UserDto;
import de.dlsa.api.entities.Role;
import de.dlsa.api.entities.Settings;
import de.dlsa.api.entities.User;
import de.dlsa.api.repositories.RoleRepository;
import de.dlsa.api.repositories.SettingsRepository;
import de.dlsa.api.repositories.UserRepository;
import de.dlsa.api.services.SettingsService;
import de.dlsa.api.responses.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SettingsService {

    private final SettingsRepository settingsRepository;
    private final ModelMapper modelMapper;

    public SettingsService(
            SettingsRepository settingsRepository,
            ModelMapper modelMapper) {
        this.settingsRepository = settingsRepository;
        this.modelMapper = modelMapper;
    }

    public Settings getSettings() {
        return settingsRepository.findOnlySettings()
                .orElseThrow(() -> new RuntimeException("Keine Einstellungen gefunden!"));
    }

    public Settings updateSettings(long id, Settings settings) {

        Settings existing = settingsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Einstellung wurde nicht gefunden!"));

        existing = modelMapper.map(settings, Settings.class);

        return settingsRepository.save(existing);
    }
}
