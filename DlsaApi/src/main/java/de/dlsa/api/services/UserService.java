package de.dlsa.api.services;

import de.dlsa.api.dtos.UserDto;
import de.dlsa.api.entities.Action;
import de.dlsa.api.entities.Role;
import de.dlsa.api.repositories.RoleRepository;
import de.dlsa.api.repositories.UserRepository;
import de.dlsa.api.entities.User;
import de.dlsa.api.responses.UserResponse;
import de.dlsa.api.shared.AlphanumComparator;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service-Klasse zur Verwaltung von Benutzern und deren Rollen.
 * Bietet Methoden zum Abrufen, Erstellen, Aktualisieren und Löschen von Benutzerdaten.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    /**
     * Konstruktor zum Initialisieren des {@link UserService}.
     *
     * @param userRepository     Repository zur Benutzerverwaltung
     * @param roleRepository     Repository zur Rollenverwaltung
     * @param passwordEncoder    Passwortverschlüsselung
     * @param modelMapper        Objekt-Mapping zwischen DTOs und Entitäten
     */
    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    /**
     * Gibt eine Liste aller Benutzer im System zurück.
     *
     * @return Liste von {@link UserResponse} Objekten
     */
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .sorted(Comparator.comparing(User::getUsername, AlphanumComparator.NATURAL_ORDER_CASE_INSENSITIVE))
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Erstellt einen neuen Benutzer mit verschlüsseltem Passwort und zugewiesener Rolle.
     *
     * @param user DTO mit Benutzerdaten
     * @return Das erstellte {@link UserResponse} Objekt
     * @throws RuntimeException wenn die übergebene Rolle nicht gefunden wird
     */
    public UserResponse createUser(UserDto user) {
        User mappedUser = modelMapper.map(user, User.class);

        Role role = roleRepository.findByRolename(user.getRole().getRolename())
                .orElseThrow(() -> new RuntimeException("Rolle nicht gefunden: " + user.getRole().getRolename()));

        mappedUser.setRole(role);
        mappedUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User addedUser = userRepository.save(mappedUser);

        return modelMapper.map(addedUser, UserResponse.class);
    }

    /**
     * Aktualisiert einen vorhandenen Benutzer anhand der übergebenen Daten.
     *
     * @param id          ID des zu aktualisierenden Benutzers
     * @param userDetails Neue Werte aus dem {@link UserDto}
     * @return Aktualisiertes {@link UserResponse} Objekt
     * @throws RuntimeException wenn der Benutzer oder die angegebene Rolle nicht gefunden wird
     */
    public UserResponse updateUser(long id, UserDto userDetails) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Benutzer wurde nicht gefunden!"));

        if (userDetails.getPassword() != null) {
            existing.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        if (userDetails.getActive() != null && !userDetails.getActive().equals(existing.getActive())) {
            existing.setActive(userDetails.getActive());
        }

        if (userDetails.getRole() != null && userDetails.getRole().getRolename() != null) {
            Role role = roleRepository.findByRolename(userDetails.getRole().getRolename())
                    .orElseThrow(() -> new RuntimeException("Rolle nicht gefunden: " + userDetails.getRole().getRolename()));
            existing.setRole(role);
        }

        User updatedUser = userRepository.save(existing);

        return modelMapper.map(updatedUser, UserResponse.class);
    }

    /**
     * Löscht einen Benutzer anhand seiner ID.
     *
     * @param id ID des zu löschenden Benutzers
     */
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
