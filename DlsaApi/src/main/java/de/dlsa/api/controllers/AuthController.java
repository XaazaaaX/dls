package de.dlsa.api.controllers;

import de.dlsa.api.dtos.LoginUserDto;
import de.dlsa.api.exceptions.UserDeactivatedException;
import de.dlsa.api.responses.AuthResponse;
import de.dlsa.api.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Verarbeitung von Anfragen für die Registrierung oder Authentifizierung eines Users
 *
 * @author Benito Ernst
 * @version  05/2025
 */
@RequestMapping("/auth")
@RestController
public class AuthController {
    private final AuthenticationService authenticationService;

    /**
     * Konstruktor
     *
     * @param authenticationService Service zur Weiterverarbeitung der Anfragen (AuthenticationService)
     */
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Endpunkt zur Registrierung eines Users
     *
     * @param loginUserDto Enthält die relevanten Userinformationen für das Login (LoginUserDto)
     * @return Rückgabe eines gültigen JWT zur Nutzung der geschützten Endpunkte der API
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginUserDto loginUserDto) throws UserDeactivatedException {
        return ResponseEntity.ok(authenticationService.login(loginUserDto));
    }
}

