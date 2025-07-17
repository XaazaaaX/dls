package de.dlsa.api.dtos;

import jakarta.validation.constraints.NotBlank;
import java.util.Collection;

/**
 * Datenübertragungsobjekt zur Erstellung oder Bearbeitung eines Bereichs (Sectors),
 * z. B. in der Vereinsverwaltung als übergeordnete Einheit für Gruppen.
 *
 * Setter folgen dem Fluent API Stil für methodisches Chaining.
 *
 * Beispiel:
 * <pre>
 *     new SectorDto()
 *         .setSectorname("Verwaltung")
 *         .setGroupIds(List.of(1L, 2L));
 * </pre>
 *
 * @author Benito Ernst
 * @version 05/2025
 */
public class SectorDto {

    /**
     * Bezeichnung des Bereichs.
     * Pflichtfeld – darf nicht leer oder nur aus Leerzeichen bestehen.
     */
    @NotBlank(message = "Bereichsbezeichnung ist erforderlich")
    private String sectorname;

    /**
     * Liste der Gruppen-IDs, die diesem Bereich zugeordnet sind (optional).
     */
    private Collection<Long> groupIds;

    // ===== Getter & Setter (Fluent API) =====

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
