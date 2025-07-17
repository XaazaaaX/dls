package de.dlsa.api.config;

import de.dlsa.api.dtos.MemberCreateDto;
import de.dlsa.api.entities.*;
import de.dlsa.api.repositories.*;
import de.dlsa.api.services.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Initialisiert Test- bzw. Standarddaten beim Start der Anwendung.
 * Die Methoden können einzeln über die `run()`-Methode aktiviert werden.
 *
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final SettingsRepository settingsRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    /**
     * Konstruktor zur Initialisierung aller benötigten Komponenten.
     */
    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           SettingsRepository settingsRepository,
                           MemberRepository memberRepository,
                           CategoryRepository categoryRepository,
                           PasswordEncoder passwordEncoder,
                           MemberService memberService) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.settingsRepository = settingsRepository;
        this.memberRepository = memberRepository;
        this.categoryRepository = categoryRepository;
        this.passwordEncoder = passwordEncoder;
        this.memberService = memberService;
    }

    /**
     * Einstiegspunkt für Dateninitialisierung beim Anwendungsstart.
     * Derzeit sind alle Initialisierungen deaktiviert.
     */
    @Override
    public void run(String... args) {
        createRoles();
        //createUser();
        //createSettings();
        //createMember();
    }

    /**
     * Erstellt Standardrollen, falls diese noch nicht existieren.
     */
    private void createRoles() {
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

    /**
     * Erstellt Standardnutzer mit festen Rollen und Passwort "admin", falls noch nicht vorhanden.
     */
    private void createUser() {
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

    /**
     * Erstellt Standardeinstellungen, wenn keine existieren.
     */
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

    /**
     * Erstellt zwei Beispiel-Mitglieder mit festen IDs, sofern diese noch nicht vorhanden sind.
     */
    private void createMember() {
        try {
            Member existing = memberRepository.findByMemberId("1111");

            if (existing == null) {
                MemberCreateDto newMember = new MemberCreateDto()
                        .setSurname("Mustermann")
                        .setForename("Max")
                        .setMemberId("1111")
                        .setEntryDate(LocalDateTime.parse("2019-03-27T00:00:00"))
                        .setBirthdate(LocalDateTime.parse("1990-03-27T00:00:00"));

                memberService.createMember(newMember);
            }

            System.out.println("Mitglied 1111 wurde initialisiert.");
        } catch (Exception e) {
            System.err.println("Fehler beim Initialisieren des Mitglieds: " + e.getMessage());
        }

        try {
            Member existing = memberRepository.findByMemberId("2222");

            if (existing == null) {
                MemberCreateDto newMember = new MemberCreateDto()
                        .setSurname("Musterfrau")
                        .setForename("Mina")
                        .setMemberId("2222")
                        .setEntryDate(LocalDateTime.parse("2019-03-27T00:00:00"))
                        .setBirthdate(LocalDateTime.parse("1990-03-27T00:00:00"));

                memberService.createMember(newMember);
            }

            System.out.println("Mitglied 2222 wurde initialisiert.");
        } catch (Exception e) {
            System.err.println("Fehler beim Initialisieren des Mitglieds: " + e.getMessage());
        }

        System.out.println("Mitglieder wurden initialisiert.");
    }
}