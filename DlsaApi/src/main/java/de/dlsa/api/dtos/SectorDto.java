package de.dlsa.api.dtos;

import java.util.ArrayList;
import java.util.Collection;

public class SectorDto {
    private String sectorname;
    //private Collection<Long> groupIds = new ArrayList<Long>();
    private Collection<Long> groupIds;

    public String getSectorname() {
        return sectorname;
    }

    public SectorDto setSectorname(String sectorname) {
        this.sectorname = sectorname;
        return this;
    }

    public Collection<Long> getGroupIds() {
        return groupIds;
    }

    public SectorDto setGroupIds(Collection<Long> groupIds) {
        this.groupIds = groupIds;
        return this;
    }
}