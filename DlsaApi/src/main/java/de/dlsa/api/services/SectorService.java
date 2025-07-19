package de.dlsa.api.services;

import de.dlsa.api.dtos.SectorDto;
import de.dlsa.api.entities.Group;
import de.dlsa.api.entities.Sector;
import de.dlsa.api.repositories.GroupRepository;
import de.dlsa.api.repositories.SectorRepository;
import de.dlsa.api.responses.SectorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviceklasse für die Verwaltung von Bereichen (Sectors).
 * Diese Klasse bietet Funktionen zum Abrufen, Erstellen und Aktualisieren von Bereichen,
 * einschließlich der Zuordnung zu Gruppen.
 */
@Service
public class SectorService {

    private final SectorRepository sectorRepository;
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    /**
     * Konstruktor zur Initialisierung der Abhängigkeiten.
     *
     * @param sectorRepository  Repository für Bereichsdaten
     * @param groupRepository   Repository für Gruppendaten
     * @param modelMapper       ModelMapper zur DTO-Konvertierung
     */
    public SectorService(
            SectorRepository sectorRepository,
            GroupRepository groupRepository,
            ModelMapper modelMapper) {
        this.sectorRepository = sectorRepository;
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Ruft alle gespeicherten Bereiche (Sectors) ab, sortiert nach ID.
     *
     * @return Liste von {@link SectorResponse}-Objekten
     */
    public List<SectorResponse> getSectors() {
        List<Sector> sectors = sectorRepository.findAll();
        return sectors.stream()
                .sorted(Comparator.comparingLong(Sector::getId).reversed())
                .map(sector -> modelMapper.map(sector, SectorResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Erstellt einen neuen Bereich und ordnet ihm (optional) Gruppen zu.
     *
     * @param sector Ein {@link SectorDto} mit den Daten des neuen Bereichs
     * @return Das erstellte {@link SectorResponse}-Objekt
     */
    public SectorResponse createSector(SectorDto sector) {

        Sector mappedSector = modelMapper.map(sector, Sector.class);

        if (sector.getGroupIds() != null) {
            List<Group> groupList = groupRepository.findAllById(sector.getGroupIds());
            mappedSector.setGroups(groupList);
        }

        Sector addedSector = sectorRepository.save(mappedSector);

        return modelMapper.map(addedSector, SectorResponse.class);
    }

    /**
     * Aktualisiert einen bestehenden Bereich anhand der übergebenen Daten.
     *
     * @param id     ID des zu aktualisierenden Bereichs
     * @param sector {@link SectorDto} mit neuen Werten
     * @return Das aktualisierte {@link SectorResponse}-Objekt
     */
    public SectorResponse updateSector(long id, SectorDto sector) {

        Sector existing = sectorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bereich wurde nicht gefunden!"));

        if (sector.getSectorname() != null) {
            existing.setSectorname(sector.getSectorname());
        }

        if (sector.getGroupIds() != null) {
            existing.setGroups(groupRepository.findAllById(sector.getGroupIds()));
        }

        Sector updatedSector = sectorRepository.save(existing);

        return modelMapper.map(updatedSector, SectorResponse.class);
    }

    /**
     * Löscht eine bestehenden Bereich anhand der ID.
     *
     * @param id     ID des zu aktualisierenden Bereichs
     */
    public void deleteSector(long id) {
        sectorRepository.deleteById(id);
    }
}
