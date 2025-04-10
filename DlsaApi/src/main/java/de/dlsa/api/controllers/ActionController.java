package de.dlsa.api.controllers;

import de.dlsa.api.dtos.ActionDto;
import de.dlsa.api.dtos.CategoryDto;
import de.dlsa.api.responses.ActionResponse;
import de.dlsa.api.responses.CategoryResponse;
import de.dlsa.api.services.ActionService;
import de.dlsa.api.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/actions")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ActionController {

    private final ActionService actionService;

    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping
    public ResponseEntity<List<ActionResponse>> getCategories() {
        return ResponseEntity.ok(actionService.getActions());
    }

    @PostMapping
    public ResponseEntity<List<ActionResponse>> createCategories(@RequestBody List<ActionDto> actions) {
        List<ActionResponse> created = actionService.createActions(actions);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActionResponse> updateCategory(@PathVariable long id, @RequestBody ActionDto action) {
        ActionResponse updated = actionService.updateAction(id, action);
        return ResponseEntity.ok(updated);
    }
}
