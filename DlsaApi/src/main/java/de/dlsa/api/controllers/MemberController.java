package de.dlsa.api.controllers;

import de.dlsa.api.dtos.MemberCreateDto;
import de.dlsa.api.dtos.MemberEditDto;
import de.dlsa.api.responses.MemberResponse;
import de.dlsa.api.services.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Verarbeitung von Anfragen zur Verwaltung von Mitgliedern
 * (Erstellen, Bearbeiten, Hochladen, Abfragen).
 *
 * Zugriffsbeschränkungen variieren je nach Operation.
 *
 * @author Benito Ernst
 * @version 05/2025
 */
@RestController
public class MemberController {

    private final MemberService memberService;

    /**
     * Konstruktor
     *
     * @param memberService Service zur Verarbeitung der Mitgliederanfragen
     */
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * Endpunkt zur Abfrage aller Mitglieder
     *
     * Öffentlich zugänglich (keine Rollenprüfung)
     *
     * @return Liste aller Mitglieder als MemberResponse
     */
    @GetMapping("/members")
    public ResponseEntity<List<MemberResponse>> getMembers() {
        return ResponseEntity.ok(memberService.getMembers());
    }

    /**
     * Endpunkt zum Erstellen eines neuen Mitglieds
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @param member Neue Mitgliedsdaten als MemberCreateDto (validiert)
     * @return Erstelltes Mitglied als MemberResponse
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @PostMapping("/member")
    public ResponseEntity<MemberResponse> createMember(@Valid @RequestBody MemberCreateDto member) {
        MemberResponse created = memberService.createMember(member);
        return ResponseEntity.ok(created);
    }

    /**
     * Endpunkt zum Hochladen mehrerer Mitglieder per Datei (z. B. CSV)
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @param file Multipart-Datei mit den Mitgliedsdaten
     * @return Liste der erstellten Mitglieder als MemberResponse
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @PostMapping("/member/upload")
    public ResponseEntity<List<MemberResponse>> uploadMembers(@RequestParam("file") MultipartFile file) {
        List<MemberResponse> created = memberService.uploadMember(file);
        return ResponseEntity.ok(created);
    }

    /**
     * Endpunkt zum Aktualisieren eines bestehenden Mitglieds
     *
     * Erlaubt für Rollen: Administrator, Benutzer
     *
     * @param id     ID des zu aktualisierenden Mitglieds
     * @param member Neue Mitgliedsdaten als MemberEditDto
     * @return Aktualisiertes Mitglied als MemberResponse
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @PutMapping("/members/{id}")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable long id, @RequestBody MemberEditDto member) {
        MemberResponse updated = memberService.updateMember(id, member);
        return ResponseEntity.ok(updated);
    }

    /**
     * Endpunkt zum Löschen eines Mitglieds
     *
     * Erlaubt für Rolle: Administrator, Benutzer
     *
     * @param id ID des zu löschenden Mitlieds
     * @return Leere Antwort mit Status 204 (No Content)
     */
    @PreAuthorize("hasAnyAuthority('Administrator', 'Benutzer')")
    @DeleteMapping("/member/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
