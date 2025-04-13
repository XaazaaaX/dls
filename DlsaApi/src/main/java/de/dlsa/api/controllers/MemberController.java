package de.dlsa.api.controllers;

import de.dlsa.api.dtos.GroupDto;
import de.dlsa.api.dtos.MemberDto;
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

    @PostMapping
    public ResponseEntity<List<MemberResponse>> createGroups(@RequestBody List<MemberDto> members) {
        List<MemberResponse> created = memberService.createMembers(members);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> updateGroup(@PathVariable long id, @RequestBody MemberDto member) {
        MemberResponse updated = memberService.updateMember(id, member);
        return ResponseEntity.ok(updated);
    }
}