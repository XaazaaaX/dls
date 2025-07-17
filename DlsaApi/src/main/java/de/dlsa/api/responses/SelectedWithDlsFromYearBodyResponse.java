package de.dlsa.api.responses;

import java.util.List;

/**
 * Repräsentiert eine Auswertungseinheit über DLS-Leistungen eines bestimmten Jahres
 * für eine ausgewählte Kategorie (z. B. Bereich, Gruppe, Sparte).
 *
 * @version 05/2025
 */
public class SelectedWithDlsFromYearBodyResponse {

    /** Beschriftung der Datenreihe, z. B. Name des Bereichs oder der Kategorie. */
    private String label;

    /** Liste der DLS-Werte, typischerweise pro Monat oder Aggregationseinheit. */
    private List<Double> data;

    // --- Getter & Fluent Setter ---

    public String getLabel() {
        return label;
    }

    public SelectedWithDlsFromYearBodyResponse setLabel(String label) {
        this.label = label;
        return this;
    }

    public List<Double> getData() {
        return data;
    }

    public SelectedWithDlsFromYearBodyResponse setData(List<Double> data) {
        this.data = data;
        return this;
    }
}
