package de.dlsa.api.services;

import de.dlsa.api.dtos.UserDto;
import de.dlsa.api.entities.Role;
import de.dlsa.api.repositories.RoleRepository;
import de.dlsa.api.repositories.UserRepository;
import de.dlsa.api.entities.User;
import de.dlsa.api.responses.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

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

    public List<UserResponse> getAllUsers() {
      List<User> users = userRepository.findAll();
      return users.stream()
              .sorted(Comparator.comparingLong(User::getId))
              .map(user -> modelMapper.map(user, UserResponse.class))
              .collect(Collectors.toList());
    }

    public List<UserResponse> createUser(List<UserDto> users) {

        List<User> newUser = new ArrayList<>();;

        for (UserDto user: users) {

            Role role = roleRepository.findByRolename(user.getRole().getRolename())
                    .orElseThrow(() -> new RuntimeException("Rolle nicht gefunden: " + user.getRole().getRolename()));

            user.setRole(role);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            newUser.add(modelMapper.map(user, User.class));
        }

        List<User> addedUsers = userRepository.saveAll(newUser);

        return addedUsers.stream()
                .sorted(Comparator.comparingLong(User::getId))
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

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

        User updatedUser =  userRepository.save(existing);

        return modelMapper.map(updatedUser, UserResponse.class);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
