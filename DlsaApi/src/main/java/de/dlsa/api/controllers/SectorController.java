package de.dlsa.api.controllers;

import de.dlsa.api.dtos.SectorDto;
import de.dlsa.api.responses.SectorResponse;
import de.dlsa.api.services.SectorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SectorController {

    private final SectorService sectorService;

    public SectorController(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    @GetMapping("/sectors")
    public ResponseEntity<List<SectorResponse>> getSectors() {
        return ResponseEntity.ok(sectorService.getSectors());
    }

    @PostMapping("/sector")
    public ResponseEntity<SectorResponse> createSector(@Valid @RequestBody SectorDto sector) {
        SectorResponse created = sectorService.createSector(sector);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/sectors/{id}")
    public ResponseEntity<SectorResponse> updateSector(@PathVariable long id, @RequestBody SectorDto sector) {
        SectorResponse updated = sectorService.updateSector(id, sector);
        return ResponseEntity.ok(updated);
    }
}