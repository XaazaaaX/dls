package de.dlsa.api.controllers;

import de.dlsa.api.dtos.ActionDto;
import de.dlsa.api.responses.ActionResponse;
import de.dlsa.api.services.ActionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActionController {

    private final ActionService actionService;

    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @GetMapping("/actions")
    public ResponseEntity<List<ActionResponse>> getActions() {
        return ResponseEntity.ok(actionService.getActions());
    }

    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @PostMapping("/action")
    public ResponseEntity<ActionResponse> createAction(@Valid @RequestBody ActionDto action) {
        ActionResponse created = actionService.createAction(action);
        return ResponseEntity.ok(created);
    }

    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @PutMapping("/actions/{id}")
    public ResponseEntity<ActionResponse> updateAction(@PathVariable long id, @RequestBody ActionDto action) {
        ActionResponse updated = actionService.updateAction(id, action);
        return ResponseEntity.ok(updated);
    }
}
