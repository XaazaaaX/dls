package de.dlsa.api.responses;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Repräsentiert einen Bereich (Sektor) mit zugehörigen Gruppen.
 *
 * @version 05/2025
 */
public class SectorResponse {

    /** Eindeutige ID des Bereichs. */
    private Long id;

    /** Name des Bereichs. */
    private String sectorname;

    /** Zugeordnete Gruppen des Bereichs. */
    private Collection<GroupResponse> groups = new ArrayList<>();

    // --- Getter & Fluent Setter ---

    public Long getId() {
        return id;
    }

    public SectorResponse setId(Long id) {
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
