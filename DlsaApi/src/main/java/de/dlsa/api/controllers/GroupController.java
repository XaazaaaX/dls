package de.dlsa.api.controllers;

import de.dlsa.api.dtos.ActionDto;
import de.dlsa.api.dtos.GroupDto;
import de.dlsa.api.responses.ActionResponse;
import de.dlsa.api.responses.GroupResponse;
import de.dlsa.api.services.ActionService;
import de.dlsa.api.services.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/groups")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getGroups() {
        return ResponseEntity.ok(groupService.getGroups());
    }

    @PostMapping
    public ResponseEntity<List<GroupResponse>> createGroups(@RequestBody List<GroupDto> groups) {
        List<GroupResponse> created = groupService.createGroups(groups);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponse> updateGroup(@PathVariable long id, @RequestBody GroupDto group) {
        GroupResponse updated = groupService.updateGroup(id, group);
        return ResponseEntity.ok(updated);
    }
}