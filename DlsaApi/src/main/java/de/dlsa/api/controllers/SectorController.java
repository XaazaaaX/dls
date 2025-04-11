package de.dlsa.api.controllers;

import de.dlsa.api.dtos.GroupDto;
import de.dlsa.api.dtos.SectorDto;
import de.dlsa.api.responses.GroupResponse;
import de.dlsa.api.responses.SectorResponse;
import de.dlsa.api.services.GroupService;
import de.dlsa.api.services.SectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/sectors")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SectorController {

    private final SectorService sectorService;

    public SectorController(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    @GetMapping
    public ResponseEntity<List<SectorResponse>> getSectors() {
        return ResponseEntity.ok(sectorService.getSectors());
    }

    @PostMapping
    public ResponseEntity<List<SectorResponse>> createGroups(@RequestBody List<SectorDto> sectors) {
        List<SectorResponse> created = sectorService.createSectors(sectors);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SectorResponse> updateGroup(@PathVariable long id, @RequestBody SectorDto sector) {
        SectorResponse updated = sectorService.updateSector(id, sector);
        return ResponseEntity.ok(updated);
    }
}