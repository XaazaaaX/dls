package de.dlsa.api.config;

import de.dlsa.api.entities.*;
import de.dlsa.api.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final SettingsRepository settingsRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           SettingsRepository settingsRepository,
                           MemberRepository memberRepository,
                           CategoryRepository categoryRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.settingsRepository = settingsRepository;
        this.memberRepository = memberRepository;
        this.categoryRepository = categoryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createRoles();
        createUser();
        createSettings();
        createMember();
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

    private void createSettings() {

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

    private void createMember() {

        try {
            memberRepository.findByMemberId("1111")
                    .orElseGet(() -> {

                        List<Category> categories = categoryRepository.findAll();

                        Member newMember = new Member()
                                .setSurname("Mustermann")
                                .setForename("Max")
                                .setMemberId("1111")
                                .setCategories(categories)
                                .setEntryDate(Instant.parse("2019-03-27T00:00:00Z"))
                                .setBirthdate(Instant.parse("2019-03-27T00:00:00Z"));

                        return memberRepository.save(newMember);
                    });

            System.out.println("Mitglied 1111 wurde initialisiert.");
        } catch (Exception e) {
            System.err.println("Fehler beim Initialisieren des Mitglieds: " + e.getMessage());
        }

        try {
            memberRepository.findByMemberId("2222")
                    .orElseGet(() -> {

                        List<Category> categories = categoryRepository.findAll();

                        Member newMember = new Member()
                                .setSurname("Musterfrau")
                                .setForename("Mina")
                                .setMemberId("2222")
                                .setCategories(categories)
                                .setEntryDate(Instant.parse("2019-03-27T00:00:00Z"))
                                .setBirthdate(Instant.parse("2019-03-27T00:00:00Z"));

                        return memberRepository.save(newMember);
                    });

            System.out.println("Mitglied 2222 wurde initialisiert.");
        } catch (Exception e) {
            System.err.println("Fehler beim Initialisieren des Mitglieds: " + e.getMessage());
        }

        System.out.println("Mitglieder wurden initialisiert.");
    }
}
