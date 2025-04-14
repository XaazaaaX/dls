package de.dlsa.api.controllers;

import de.dlsa.api.dtos.MemberDto;
import de.dlsa.api.responses.MemberResponse;
import de.dlsa.api.services.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponse>> getMembers() {
        return ResponseEntity.ok(memberService.getMembers());
    }

    @PostMapping("/member")
    public ResponseEntity<MemberResponse> createGroups(@Valid @RequestBody MemberDto member) {
        MemberResponse created = memberService.createMember(member);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<MemberResponse> updateGroup(@PathVariable long id, @RequestBody MemberDto member) {
        MemberResponse updated = memberService.updateMember(id, member);
        return ResponseEntity.ok(updated);
    }
}