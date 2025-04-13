package de.dlsa.api.controllers;

import de.dlsa.api.dtos.GroupDto;
import de.dlsa.api.responses.GroupResponse;
import de.dlsa.api.responses.MemberResponse;
import de.dlsa.api.services.GroupService;
import de.dlsa.api.services.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/members")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers() {
        return ResponseEntity.ok(memberService.getMembers());
    }

    /*
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

     */
}