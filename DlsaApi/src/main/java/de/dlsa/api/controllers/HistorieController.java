package de.dlsa.api.controllers;

import de.dlsa.api.responses.ActionResponse;
import de.dlsa.api.responses.GroupChangesResponse;
import de.dlsa.api.responses.MemberChangesResponse;
import de.dlsa.api.services.ActionService;
import de.dlsa.api.services.HistorieService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/historie")
public class HistorieController {

    private final HistorieService historieService;

    public HistorieController(HistorieService historieService) {
        this.historieService = historieService;
    }

    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @GetMapping("/groups")
    public ResponseEntity<List<GroupChangesResponse>> getGroupChanges() {
        return ResponseEntity.ok(historieService.getGroupChanges());
    }

    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @GetMapping("/members")
    public ResponseEntity<List<MemberChangesResponse>> getMemberChanges() {
        return ResponseEntity.ok(historieService.getMemberChanges());
    }
}