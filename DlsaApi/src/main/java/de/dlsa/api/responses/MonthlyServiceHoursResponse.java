package de.dlsa.api.responses;

import java.util.List;

/**
 * Repräsentiert die monatlich erfassten DLS-Leistungen für eine bestimmte Entität (z. B. Bereich, Gruppe, Sparte).
 * Enthält die Beschriftung sowie die entsprechenden Werte pro Monat.
 *
 * @version 05/2025
 */
public class MonthlyServiceHoursResponse {

    /** Bezeichnung für die Datenreihe, z. B. Name des Bereichs oder der Gruppe. */
    private String label;

    /** Liste der DLS-Werte pro Monat (Index 0 = Januar, Index 11 = Dezember). */
    private List<Double> data;

    // --- Getter & Fluent Setter ---

    public String getLabel() {
        return label;
    }

    public MonthlyServiceHoursResponse setLabel(String label) {
        this.label = label;
        return this;
    }

    public List<Double> getData() {
        return data;
    }

    public MonthlyServiceHoursResponse setData(List<Double> data) {
        this.data = data;
        return this;
    }
}
