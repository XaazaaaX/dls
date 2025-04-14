package de.dlsa.api.controllers;

import de.dlsa.api.dtos.GroupDto;
import de.dlsa.api.responses.GroupResponse;
import de.dlsa.api.services.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    public ResponseEntity<List<GroupResponse>> getGroups() {
        return ResponseEntity.ok(groupService.getGroups());
    }

    @PostMapping("/group")
    public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody GroupDto group) {
        GroupResponse created = groupService.createGroup(group);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/groups/{id}")
    public ResponseEntity<GroupResponse> updateGroup(@PathVariable long id, @RequestBody GroupDto group) {
        GroupResponse updated = groupService.updateGroup(id, group);
        return ResponseEntity.ok(updated);
    }
}