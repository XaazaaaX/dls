package de.dlsa.api.responses;

import de.dlsa.api.entities.Group;
import de.dlsa.api.entities.Sector;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

public class SectorResponse {
    private Long id;
    private String sectorname;
    private Collection<GroupResponse> groups = new ArrayList<GroupResponse>();

    public Long getId() {
        return id;
    }

    public SectorResponse setId(Long id){
        this.id = id;
        return this;
    }

    public String getSectorname() {
        return sectorname;
    }

    public SectorResponse setSectorname(String sectorname) {
        this.sectorname = sectorname;
        return this;
    }

    public Collection<GroupResponse> getGroups() {
        return groups;
    }

    public SectorResponse setGroups(Collection<GroupResponse> groups) {
        this.groups = groups;
        return this;
    }
}
