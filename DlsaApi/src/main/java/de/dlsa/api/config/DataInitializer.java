package de.dlsa.api.config;

import de.dlsa.api.entities.Role;
import de.dlsa.api.entities.Settings;
import de.dlsa.api.entities.User;
import de.dlsa.api.repositories.RoleRepository;
import de.dlsa.api.repositories.SettingsRepository;
import de.dlsa.api.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final SettingsRepository settingsRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           SettingsRepository settingsRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.settingsRepository = settingsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createRoles();
        createUser();
        createSettings();
    }

    private void createRoles(){
        List<String> standardRoles = List.of("Administrator", "Benutzer", "Gast");

        for (String roleName : standardRoles) {
            roleRepository.findByRolename(roleName)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setRolename(roleName);
                        return roleRepository.save(newRole);
                    });
        }

        System.out.println("Rollen wurden initialisiert.");
    }

    private void createUser() {
        // Map mit username → rolename
        Map<String, String> standardUsers = Map.of(
                "admin", "Administrator",
                "max", "Benutzer",
                "lisa", "Gast"
        );

        for (Map.Entry<String, String> entry : standardUsers.entrySet()) {
            String username = entry.getKey();
            String rolename = entry.getValue();

            userRepository.findByUsername(username)
                    .orElseGet(() -> {
                        Role role = roleRepository.findByRolename(rolename)
                                .orElseThrow(() -> new RuntimeException("Rolle nicht gefunden: " + rolename));

                        User newUser = new User()
                                .setUsername(username)
                                .setPassword(passwordEncoder.encode("admin")) // Standardpasswort
                                .setActive(true)
                                .setRole(role);
                        return userRepository.save(newUser);
                    });
        }

        System.out.println("User wurden initialisiert.");
    }

    public void createSettings() {

        settingsRepository.findOnlySettings()
                .orElseGet(() -> {
                    Settings newSettings = new Settings()
                            .setDueDate("31.12")
                            .setCountDls(5.0)
                            .setCostDls(10.0)
                            .setAgeFrom(18)
                            .setAgeTo(67)
                            .setBookingMethod("Anteilig bis zum Stichtag")
                            .setClearing(true)
                            .setGranularity("Keine");
                    return settingsRepository.save(newSettings);
                });

        System.out.println("Einstellungen wurden initialisiert.");
    }
}
