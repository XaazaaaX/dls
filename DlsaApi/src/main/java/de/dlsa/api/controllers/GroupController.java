package de.dlsa.api.controllers;

import de.dlsa.api.dtos.GroupDto;
import de.dlsa.api.responses.GroupResponse;
import de.dlsa.api.services.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer', 'Gast')")
    @GetMapping("/groups")
    public ResponseEntity<List<GroupResponse>> getGroups() {
        return ResponseEntity.ok(groupService.getGroups());
    }

    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @PostMapping("/group")
    public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody GroupDto group) {
        GroupResponse created = groupService.createGroup(group);
        return ResponseEntity.ok(created);
    }

    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @PutMapping("/groups/{id}")
    public ResponseEntity<GroupResponse> updateGroup(@PathVariable long id, @RequestBody GroupDto group) {
        GroupResponse updated = groupService.updateGroup(id, group);
        return ResponseEntity.ok(updated);
    }
}