package de.dlsa.api.controllers;

import de.dlsa.api.dtos.MemberCreateDto;
import de.dlsa.api.dtos.MemberEditDto;
import de.dlsa.api.responses.MemberResponse;
import de.dlsa.api.services.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/members")
    public ResponseEntity<List<MemberResponse>> getMembers() {
        return ResponseEntity.ok(memberService.getMembers());
    }

    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @PostMapping("/member")
    public ResponseEntity<MemberResponse> createMember(@Valid @RequestBody MemberCreateDto member) {
        MemberResponse created = memberService.createMember(member);
        return ResponseEntity.ok(created);
    }

    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @PostMapping("/member/upload")
    public ResponseEntity<List<MemberResponse>> uploadMembers(@RequestParam("file") MultipartFile file) {
        List<MemberResponse> created = memberService.uploadMember(file);
        return ResponseEntity.ok(created);
    }

    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @PutMapping("/members/{id}")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable long id, @RequestBody MemberEditDto member) {
        MemberResponse updated = memberService.updateMember(id, member);
        return ResponseEntity.ok(updated);
    }
}