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

@Service
public class SectorService {

    private final SectorRepository sectorRepository;
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    public SectorService(
            SectorRepository sectorRepository,
            GroupRepository groupRepository,
            ModelMapper modelMapper) {
        this.sectorRepository = sectorRepository;
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
    }

    public List<SectorResponse> getSectors() {
        List<Sector> sectors = sectorRepository.findAll();
        return sectors.stream()
                .sorted(Comparator.comparingLong(Sector::getId))
                .map(sector -> modelMapper.map(sector, SectorResponse.class))
                .collect(Collectors.toList());
    }

    public SectorResponse createSector(SectorDto sector) {

        Sector mappedSector = modelMapper.map(sector, Sector.class);

        if (sector.getGroupIds() != null) {
            List<Group> groupList = groupRepository.findAllById(sector.getGroupIds());
            mappedSector.setGroups(groupList);
        }

        Sector addedSector = sectorRepository.save(mappedSector);

        return modelMapper.map(addedSector, SectorResponse.class);
    }

    public SectorResponse updateSector(long id, SectorDto sector) {

        Sector existing = sectorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bereich wurde nicht gefunden!"));

        if (sector.getSectorname() != null) {
            existing.setSectorname(sector.getSectorname());
        }

        if (sector.getGroupIds() != null) {
            existing.setGroups(groupRepository.findAllById(sector.getGroupIds()));
        }

        Sector updatedSector =  sectorRepository.save(existing);

        return modelMapper.map(updatedSector, SectorResponse.class);
    }


}